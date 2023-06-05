import { Component, Output } from '@angular/core';
import { CdkDragDrop, moveItemInArray, transferArrayItem } from '@angular/cdk/drag-drop';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Character, Daily, EditDaily, EditHabit, EditToDo, EditedReward, Habit, Reward, ToDo } from '../_models/player';
import { DataService } from '../_services/data.service';
import { TaskService } from '../_services/task.service';
import { Observable, Subject, lastValueFrom, tap } from 'rxjs';
import { MatSnackBar } from '@angular/material/snack-bar';
import { Router } from '@angular/router';
import { CharacterService } from '../_services/character.service';
import { Store } from '@ngrx/store';
import { increment, decrement } from '../_ngrx_store/counter.actions';
import * as PlayerActions from '../_ngrx_store/game-section.redux';
import { PlayerstateService } from '../_shared/playerstate.service';
import { Player } from '../_shared/models';
import * as Audio from '../_shared/sound';
import Validation from '../utils/validation';

@Component({
  selector: 'app-habits',
  templateUrl: './habits.component.html',
  styleUrls: ['./habits.component.css'],
})
export class HabitsComponent {
  userId!: string;

  //mini forms for quick entry (at the top of the list)
  habitsForm!: FormGroup;
  dailiesForm!: FormGroup;
  todosForm!: FormGroup;
  rewardsForm!: FormGroup;
  //editing forms for modals
  habitsEditForm!: FormGroup;
  dailiesEditForm!: FormGroup;
  todosEditForm!: FormGroup;
  rewardsEditForm!: FormGroup;

  habits: Habit[] = [];
  dailies: Daily[] = [];
  todos: ToDo[] = [];
  rewards: Reward[] = [];
  isEditing: boolean = false;
  isGameSectionLocked: boolean = true;
  //hold my temporary session data
  player = {
    health: 0,
    coinwallet: 20,
    currentpetid: 0,
    image: 'character.avif',
    isGameSectionLocked: true,
    petimage: 'dragon.avif',
    enemyhealth: 0,
    enemyimage: 'enemy.webp'
  };

  constructor(
    private fb: FormBuilder,
    private dataSvc: DataService,
    private taskSvc: TaskService,
    private charaSvc: CharacterService,
    private router: Router,
    private _snackBar: MatSnackBar,
    public playerStateSvc: PlayerstateService,
    // private store: Store
  ) {
    this.playerStateSvc.onPlayerData.subscribe(
      (playerData) => {
        console.log(playerData)
        this.player = playerData
      });


    //connects to the count state we specified above
    // this.count$ = this.store.select((state: PlayerActions.State) => PlayerActions.selectCounter(state as object));

    //TODO - refactor the whole app into using ngrx
    //having a lot of trouble w versions so switched to using service. ngrx is abit overkill tbh

    //load the sounds
    Audio.coin.load();
  }

  get getSharedNode() {
    return this.playerStateSvc.sharedNode;
  }

  // increment() {
  //   this.store.dispatch(increment());
  // }

  // decrement() {
  //   this.store.dispatch(decrement());
  // }

  // unlockGameSection() {
  //   this.store.dispatch(PlayerActions.gameUnlock());
  // }
  // lockGameSection() {
  //   this.store.dispatch(PlayerActions.gameLock());
  // }
  ngOnInit(): void {
    console.log("loading habits... ")
    //get shared node


    //if player logged in
    //fetch habits and player details
    console.log("habits>> getting userid", localStorage.getItem("userid"))
    if (!!localStorage.getItem("userid")) {
      // console.log("getting tasks for userid:", fetchUserId)
      // @ts-ignore
      this.userId = localStorage.getItem("userid")
      this.dataSvc.getAllTasks(this.userId)
        .then(
          (result => {
            console.log("received tasks: ", result)
            this.habits = result.habits
            this.todos = result.todos
            this.dailies = result.dailies
            this.rewards = result.rewards
          })
        ).catch(
          error => console.error("request for user tasks data from server failed", error)
        )
    }

    //init forms =============================
    this.habitsForm = this.createForm();
    this.dailiesForm = this.createForm();
    this.todosForm = this.createForm();
    this.rewardsForm = this.createForm();

    this.rewardsEditForm = this.createRewardsEditForm();
    this.todosEditForm = this.createToDoEditForm();
    this.habitsEditForm = this.createHabitsEditForm();
    this.dailiesEditForm = this.createDailyEditForm();
  }
  private createForm(): FormGroup {
    //creates obj with default
    return this.fb.group({
      title: this.fb.control('', [
        Validators.required,
      ])
    });
  }

  //DONE
  private createRewardsEditForm() {
    return this.fb.group({
      arrayid: this.fb.control(''),
      // userid: this.fb.control(''),
      rewardid: this.fb.control(''),
      title: this.fb.control('', [
        Validators.required,
      ]),
      cost: this.fb.control('', [
        Validators.required
      ]),
      dateCreated: this.fb.control(''),
    });
  }
  //DONE
  private createToDoEditForm() {
    return this.fb.group({
      arrayid: this.fb.control(''),
      // userid: this.fb.control(''),
      todoid: this.fb.control(''),
      title: this.fb.control('', [
        Validators.required,
      ]),
      difficulty: this.fb.control(''),
      dueDate: this.fb.control('', [
        Validators.required,
        Validation.futureDateValidator()
      ]),
      priority: this.fb.control('', [
        Validators.required,
      ]),
      isComplete: this.fb.control(''),
      dateCreated: this.fb.control('')
    });
  }

  //DONE
  private createHabitsEditForm() {
    return this.fb.group({
      arrayid: this.fb.control(''),
      // userid: this.fb.control(''),
      habitid: this.fb.control(''),
      title: this.fb.control('', [
        Validators.required,
      ]),
      isGoodorBadHabit: this.fb.control(''),
      difficulty: this.fb.control(''),
      positiveCount: this.fb.control(''),
      negativeCount: this.fb.control(''),
      dateCreated: this.fb.control('')
    });
  }
  //DONE
  private createDailyEditForm() {
    return this.fb.group({
      arrayid: this.fb.control(''),
      // userid: this.fb.control(''),
      dailyid: this.fb.control(''),
      title: this.fb.control(''),
      difficulty: this.fb.control('', [
        Validators.required,
      ]),
      isComplete: this.fb.control('', [
        Validators.required
      ]),
      dateCreated: this.fb.control(''),
    });
  }


  // list drag n drop ===============================
  drop(event: CdkDragDrop<any[]>): void {
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      transferArrayItem(event.previousContainer.data,
        event.container.data,
        event.previousIndex,
        event.currentIndex);
    }
  }

  //generic task ==================================
  addItem(itemListType: string) {
    switch (itemListType) {
      case "dailyItem": {
        let daily: Daily = {
          //@ts-ignore
          userid: localStorage.getItem("userid"),
          dailyid: 0,
          title: this.dailiesForm.get('title')?.value,
          difficulty: "low",
          isComplete: false,
          dateCreated: this.getNewDate()
        };
        console.log('addItem(): saving to daily>>', daily);

        //send to backend
        this.taskSvc.addDaily(daily)
          .then((result) => {
            console.log(result)
            //add dailies to list
            daily.dailyid = result.dailyid
            console.log(daily)
            this.dailies.unshift(daily);
            this.dailiesForm.reset();
            console.log("add item to dailies")
          }
          )
          .catch((error) => {
            console.log(error)
          }
          );
        break;

      }

      case "todoItem": {
        const todo: ToDo = {
          //@ts-ignore
          userid: localStorage.getItem("userid"),
          todoid: 0,
          title: this.todosForm.get('title')?.value,
          difficulty: "low",
          dueDate: new Date(), //yyyy-mm-dd. sorted nearest time at the top.
          priority: "low",
          isComplete: false,
          dateCreated: this.getNewDate()
        };
        console.log('addItem(): saving to todo>>', todo);
        this.taskSvc.addTodo(todo)
          .then((result) => {
            console.log(result)
            //add todo at the start of list
            todo.todoid = result.todoid
            console.log("added todoid", todo)
            this.todos.unshift(todo);
            // sessionStorage.setItem("todosList", JSON.stringify(this.todos));
            this.todosForm.reset();
            console.log("add item to todo")
          })
          .catch((error) => {
            console.log(error)
          });
        break;
      }
      case "habitItem": {
        //retrieve the data
        const habit: Habit = {
          //@ts-ignore
          userid: localStorage.getItem("userid"),
          habitid: 0,
          title: this.habitsForm.get('title')?.value,
          isGoodorBadHabit: "both",
          difficulty: "easy",
          positiveCount: 0,
          negativeCount: 0,
          dateCreated: this.getNewDate()
        };
        console.log('addItem(): saving habit>>', habit);

        //add habit
        this.taskSvc.addHabit(habit)
          .then(
            (result) => {
              console.log("add habit success: ", result)
              habit.habitid = result.habitid
              this.habits.unshift(habit);
              // sessionStorage.setItem("habitsList", JSON.stringify(this.rewards));
              this.habitsForm.reset();
              console.log("add item to habits", result)
            })
          .catch(
            (error) => console.error('failed to add habit', error)
          );

        break;
      }
      case "rewardItem": {
        const reward: Reward = {
          //@ts-ignore
          userid: localStorage.getItem("userid"),
          rewardid: 0,
          title: this.rewardsForm.get('title')?.value,
          cost: 10,
          dateCreated: this.getNewDate()
        };
        console.log('addItem(): saving to rewards>>', reward);

        //add reward
        this.taskSvc.addReward(reward).then(
          (result) => {

            console.log("add reward success, result: ", result)
            reward.rewardid = result.rewardid;

            console.log("reward with rewardid", reward)
            this.rewards.unshift(reward);
            // sessionStorage.setItem("rewardsList", JSON.stringify(this.rewards));
            this.rewardsForm.reset();
            console.log("added item to rewards")
          }
        ).catch(
          (error) => console.log(error)
        );
        break;
      }
      default: {
        console.error("list not found, check the dailies/todo/habits/rewards names");
        break;
      }
    }
  }

  deleteHabit(index: number) {
    this.taskSvc.deleteHabit(this.habits[index]).then(
      (result) => {
        const deletedHabit = this.habits.splice(index, 1);
        console.log("deleted habit", deletedHabit)
      }
    ).catch(error => console.log(error));
  }

  deleteDaily(index: number) {
    this.taskSvc.deleteDaily(this.dailies[index]).then(
      (result) => {
        const deleteddaily = this.dailies.splice(index, 1);
        console.log("deleted daily", deleteddaily)
      }
    ).catch(error => console.log(error)
    );
  }

  deleteToDo(index: number) {
    this.taskSvc.deleteTodo(this.todos[index]).then(
      (result) => {
        const deletedTodo = this.todos.splice(index, 1);
        console.log('deleted todo: ', deletedTodo);
      }
    ).catch(error => console.log(error));
  }

  deleteReward(index: number) {
    this.taskSvc.deleteReward(this.rewards[index]).then(
      (result) => {
        const deletedReward = this.rewards.splice(index, 1);
        console.log('deleted reward: ', deletedReward);
      }
    ).catch(error => console.log(error));
  }


  //DONE
  selectHabit(index: number) {
    this.habitsEditForm = this.createHabitsEditForm();
    this.isEditing = true;
    const editingHabit = this.habits[index] as Habit;

    console.log("editing Habit ", editingHabit)

    this.habitsEditForm.get('arrayid')?.setValue(index)
    this.habitsEditForm.get('habitid')?.setValue(editingHabit.habitid)
    this.habitsEditForm.get('title')?.setValue(editingHabit.title)
    this.habitsEditForm.get('isGoodorBadHabit')?.setValue(editingHabit.isGoodorBadHabit)
    this.habitsEditForm.get('difficulty')?.setValue(editingHabit.difficulty)
    this.habitsEditForm.get('positiveCount')?.setValue(editingHabit.positiveCount)
    this.habitsEditForm.get('negativeCount')?.setValue(editingHabit.negativeCount)
    this.habitsEditForm.get('dateCreated')?.setValue(editingHabit.dateCreated)

  }
  // DONE
  selectDaily(index: number) {
    this.dailiesEditForm = this.createDailyEditForm();
    this.isEditing = true;
    const editingDaily = this.dailies[index] as Daily;

    console.log("editing Habit ", editingDaily)

    this.dailiesEditForm.get('arrayid')?.setValue(index)
    this.dailiesEditForm.get('dailyid')?.setValue(editingDaily.dailyid)
    this.dailiesEditForm.get('title')?.setValue(editingDaily.title)
    this.dailiesEditForm.get('difficulty')?.setValue(editingDaily.difficulty)
    this.dailiesEditForm.get('isComplete')?.setValue(editingDaily.isComplete)
    this.dailiesEditForm.get('dateCreated')?.setValue(editingDaily.dateCreated)
  }

  //DONE
  selectTodo(index: number) {
    this.todosEditForm = this.createToDoEditForm();
    this.isEditing = true;
    const editingTodo = this.todos[index] as ToDo;

    console.log("editing Todo ", editingTodo)

    this.todosEditForm.get('arrayid')?.setValue(index)
    this.todosEditForm.get('todoid')?.setValue(editingTodo.todoid)
    this.todosEditForm.get('title')?.setValue(editingTodo.title)
    this.todosEditForm.get('difficulty')?.setValue(editingTodo.difficulty)
    this.todosEditForm.get('dueDate')?.setValue(editingTodo.dueDate)
    this.todosEditForm.get('isComplete')?.setValue(editingTodo.isComplete)
    this.todosEditForm.get('dateCreated')?.setValue(editingTodo.dateCreated)

  }
  //DONE
  selectReward(index: number) {
    this.rewardsEditForm = this.createRewardsEditForm();
    this.isEditing = true;
    const editingreward = this.rewards[index] as Reward;

    console.log("editing reward ", editingreward)

    this.rewardsEditForm.get('arrayid')?.setValue(index)
    this.rewardsEditForm.get('rewardid')?.setValue(editingreward.rewardid)
    this.rewardsEditForm.get('title')?.setValue(editingreward.title)
    this.rewardsEditForm.get('cost')?.setValue(editingreward.cost)
    this.rewardsEditForm.get('dateCreated')?.setValue(editingreward.dateCreated)

  }

  saveEditHabit() {
    let editedHabit = this.habitsEditForm.value as EditHabit;
    const arrayid = editedHabit.arrayid;
    editedHabit = Object.assign(editedHabit, { userid: localStorage.getItem('userid') })
    console.log("saving edited Habit", editedHabit)

    //TODO
    // send to backend
    this.taskSvc.editHabit(editedHabit)
      .then((result) => {
        // const modifiedToDoIndex = result.arrayid;
        console.log("modified Daily arrayid", arrayid)
        this.habits[arrayid] = editedHabit

      })
      .catch((error) => { console.log("edit Daily failed", error) });
  }

  //DONE
  saveEditDaily() {
    let editedDaily = this.dailiesEditForm.value as EditDaily;
    const arrayid = editedDaily.arrayid;
    editedDaily = Object.assign(editedDaily, { userid: localStorage.getItem('userid') })
    console.log("saving edited Todo", editedDaily)

    // send to backend
    this.taskSvc.editDaily(editedDaily)
      .then((result) => {
        // const modifiedToDoIndex = result.arrayid;
        console.log("modified Daily arrayid", arrayid)
        this.dailies[arrayid] = editedDaily

      })
      .catch((error) => { console.log("edit Daily failed", error) });
  }

  //DONE
  saveEditToDo() {
    let editedTodo = this.todosEditForm.value as EditToDo;
    const arrayid = editedTodo.arrayid;
    editedTodo = Object.assign(editedTodo, { userid: localStorage.getItem('userid') })
    console.log("saving edited Todo", editedTodo)
    //send to backend
    this.taskSvc.editTodo(editedTodo)
      .then((result) => {
        // const modifiedToDoIndex = result.arrayid;
        console.log("modified Todo arrayid", arrayid)
        this.todos[arrayid] = editedTodo

      })
      .catch((error) => { console.log("editReward failed", error) });
  }

  //DONE
  saveEditReward() {
    const editedReward = this.rewardsEditForm.value as EditedReward;
    console.log("saving edited Reward", editedReward)
    const arrayid = editedReward.arrayid;
    //send to backend
    this.taskSvc.editReward(editedReward)
      .then((result) => {
        //backend sends back the array index of the modded task as confirmation
        const modifiedRewardIndex = result.arrayid;

        console.log("modified reward arrayid", modifiedRewardIndex)
        //find and replace the reward
        this.rewards[modifiedRewardIndex] = editedReward

      })
      .catch((error) => { console.log("editReward failed", error) });
  }

  //habits =========================================
  addToGoodCounter(index: number) {
    console.log("adding to habit", this.habits[index].title)
    let habitsClone = Object.assign({}, this.habits[index]);
    habitsClone.positiveCount++
    const habitsClone2 = Object.assign(habitsClone, { arrayid: index });
    Audio.coin.play()
    console.log(habitsClone2)
    this.taskSvc.editHabit(habitsClone2)
      .then((result) => {
        console.log("successful updated habit", result)
        this.habits[index].positiveCount++;
        this.earnCoin(10)
      })
      .catch((error) => {
        console.error(error)
      });

  }

  addToBadCounter(index: number) {
    console.log("adding to habit", this.habits[index].title)

    let habitsClone = Object.assign({}, this.habits[index]);
    habitsClone.negativeCount++
    const habitsClone2 = Object.assign(habitsClone, { arrayid: index });

    console.log(habitsClone2)
    this.taskSvc.editHabit(habitsClone2)
      .then((result) => {
        console.log("successful updated habit", result)
        this.habits[index].negativeCount++;
        this.consumeCoin(10)
      })
      .catch((error) => {
        console.error(error)
      });



  }

  // ToDo =======================================
  onCompleteToDo(index: number) {
    const todo = this.todos[index];
    let todoClone = Object.assign({}, todo)

    const coinAmount = this.calcTaskWorth(todoClone.difficulty)

    if (todoClone.isComplete) {
      //undo the money we gave
      console.log('todo isComplete:', todo.isComplete)
      todo.isComplete = false;
      const editedTodo = Object.assign(todoClone, { arrayid: index })
      this.taskSvc.editTodo(editedTodo).then(
        (result) => {
          console.log("edit todo success", result);
          this.consumeCoin(coinAmount)
        },
        (error) => {
          console.log(" edit todo failed", error);
        }
      );
    } else {
      // console.log('todo isComplete:', todo.isComplete)
      todo.isComplete = true;
      Audio.coin.play();
      console.log("sending todo in DB: ", todo.title)
      const editedTodo = Object.assign(todoClone, { arrayid: index })
      //update database
      this.taskSvc.editTodo(editedTodo).then(
        (result) => {
          console.log("edit todo success", result);
          this.earnCoin(coinAmount)
        },
        (error) => {
          console.log(" edit todo failed", error);
        }
      );
    }
  }

  // Daily =======================================
  onCompleteDaily(index: number) {
    console.log("clicked on task index:", index)

    let daily = this.dailies[index];

    //do a copy for test send to database.
    let dailyClone = Object.assign({}, daily)
    const coinAmount = this.calcTaskWorth(dailyClone.difficulty);

    //toggles if necessary
    if (dailyClone.isComplete) {
      //undo the money we gave
      dailyClone.isComplete = false;
      //slot in the array id
      const editedDaily = Object.assign(dailyClone, { arrayid: index })
      this.taskSvc.editDaily(editedDaily).then(
        (result) => {
          console.log("edit daily success", result);
          daily.isComplete = false;
          console.log("updating daily", daily)
          //TODO - take away money depending on the difficulty
          this.consumeCoin(coinAmount)
        },
        (error) => {
          console.log(" edit daily failed", error);
          alert('edit daily failed!')
        }
      );
    } else {
      //shift the task to its complete task list
      dailyClone.isComplete = true;
      Audio.coin.play(); // here for performance
      console.log("completed daily: ", daily.title)
      const editedDaily = Object.assign(dailyClone, { arrayid: index })
      //update database
      this.taskSvc.editDaily(editedDaily).then(
        (result) => {
          console.log("edit daily success", result);
          daily.isComplete = true
          //TODO - add money depending on the difficulty
          console.log('task coin worth', coinAmount)
          this.earnCoin(coinAmount);
        },
        (error) => {
          console.log(" edit daily failed", error);
          alert('edit daily failed!')
          daily.isComplete = false
        }
      );
    }
  }



  buyReward(idx: number) {
    this.consumeCoin(this.rewards[idx].cost)
    console.log("trying to spend coins on reward: " + this.rewards[idx].title)
  }


  unlockGamesSection(cost: number) {
    let player = this.playerStateSvc.sharedNode

    //check if locked
    if (player.isGameSectionLocked) {
      player.isGameSectionLocked = false;
      this.playerStateSvc.update.next(player)
      this.consumeCoin(cost)
      console.log("editing player shared node", this.playerStateSvc.sharedNode)
      this._snackBar.open("Unlocked Games Section!");
    } else {
      this._snackBar.open("You already unlocked it!");
    }
  }

  //helpers ===========================================

  //works for todos and dailies
  //calculate the coin we need to add or take
  //since ifelse only has a slowdown when above 5 or more
  //if else is a lot more readable
  //https://www.oreilly.com/library/view/high-performance-javascript/9781449382308/ch04.html
  calcTaskWorth(difficulty: string) {
    if (difficulty == "low") {
      return 4;
    }
    if (difficulty == "med") {
      return 8;
    }
    if (difficulty == "high") {
      return 12;
    }
    return 0;
  }

  //eventdriven. goes to navbar and banner
  consumeCoin(amount: number) {
    const player = this.getSharedNode;
    console.log("current player coin amount", player.coinwallet)

    if (player.coinwallet - amount < 0) {
      this._snackBar.open("Not enough money! (╥︣﹏᷅╥) Do more tasks to earn some!");
    } else {
      player.coinwallet -= amount
      this.playerStateSvc.update.next(player)
      this._snackBar.open('Spent ' + amount + " coins!");
      console.log("coins left: ", this.player.coinwallet)
    }
  }

  //eventdriven. goes to navbar and banner
  earnCoin(amount: number) {
    const player = this.getSharedNode;
    player.coinwallet += amount
    this._snackBar.open('Earned ' + amount + " coins!");
    this.playerStateSvc.update.next(player)
  }

  //eventdriven
  attackEnemy(dmgAmount: number) {
    const player = this.getSharedNode
    console.log("attacking enemy, playerstats:", player)
    this._snackBar.open('Attacked for ' + dmgAmount + " damage!");
    this.playerStateSvc.update.next(player)
  }

  padTo2Digits(num: number) {
    return num.toString().padStart(2, '0');
  }
  getNewDate() {
    return ([new Date().getFullYear(),
    this.padTo2Digits(new Date().getMonth() + 1),
    this.padTo2Digits(new Date().getDate()),
    ].join('-'))
  }

  // isControlInvalid(ctrlName: string, formname: string): boolean {
  //   const ctrl = this.formgroup.get(ctrlName) as FormControl;
  //   return ctrl.invalid && !ctrl.pristine;
  // }
}
