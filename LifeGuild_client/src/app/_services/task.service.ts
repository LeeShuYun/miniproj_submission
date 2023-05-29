import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Daily, EditDaily, EditHabit, EditToDo, EditedReward, Habit, Reward, Task, ToDo } from '../_models/player';
import { Observable, firstValueFrom, lastValueFrom } from 'rxjs';
import { API_URL } from '../_models/constant'


const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8')

@Injectable({
  providedIn: 'root'
})
export class TaskService {

  //individual CRUD postings which are a hassle
  //however to prevent losses of data this is necessary,
  // since front and backend are separate
  // TODO we need to include the JWT token

  constructor(private httpClient: HttpClient) { }

  //DONE
  addHabit(habit: Habit): Promise<any> {
    const body = habit
    console.log("taskSvc PUT /api/task/habit: ", body)
    return firstValueFrom(this.httpClient.put<Habit>(`${API_URL}/api/task/habit`, body, { headers }))
  }

  //DONE
  editHabit(edithabit: EditHabit): Promise<any> {
    const body = edithabit
    console.log("update habit: ", body)
    return firstValueFrom(this.httpClient.post<Habit>(`${API_URL}/api/task/habit/update`, body, { headers }))
  }
  //DONE
  deleteHabit(habit: Habit): Promise<any> {
    console.log("deleting habit POST ", habit)
    const body = habit.habitid
    return firstValueFrom(this.httpClient.post<Habit>(`${API_URL}/api/task/habit`, body, { headers }));

  }

  //DONE
  addDaily(daily: Daily): Promise<any> {
    const body = daily;
    console.log("put adding daily", body)
    return firstValueFrom(this.httpClient.put<Daily>(`${API_URL}/api/task/daily`, body, { headers }))
  }
  //DONE
  editDaily(editeddaily: EditDaily): Promise<any> {
    const body = editeddaily;
    console.log("edit POST /api/task/daily/update", body)
    return firstValueFrom(this.httpClient.post<Daily>(`${API_URL}/api/task/daily/update`, body, { headers }))
  }

  //DONE
  deleteDaily(daily: Daily): Promise<any> {
    const body = daily.dailyid;
    console.log("delete POST /api/task/daily", daily.dailyid)
    return firstValueFrom(this.httpClient.post<any>(`${API_URL}/api/task/daily`, body, { headers }))
  }

  //crud for todo /api/task/todo/
  //DONE
  addTodo(todo: ToDo): Promise<any> {
    console.log("PUT add /api/task/todo", todo)
    const body = todo;
    return firstValueFrom(this.httpClient.put<ToDo>(`${API_URL}/api/task/todo`, todo, { headers }))
  }
  //DONE
  editTodo(editedtodo: EditToDo): Promise<any> {
    console.log("PUT edit /api/task/todo/update", editedtodo)
    const body = editedtodo;
    return firstValueFrom(this.httpClient.post<ToDo>(`${API_URL}/api/task/todo/update`, body, { headers }))
  }

  //DONE
  deleteTodo(todo: ToDo): Promise<any> {
    console.log("DELETE POST /api/task/todo", todo)
    const body = todo.todoid;
    return firstValueFrom(this.httpClient.post<ToDo>(`${API_URL}/api/task/todo`, body, { headers }))
  }

  //DONE
  addReward(reward: Reward): Promise<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8')
    console.log("add PUT /api/task/reward: ", reward)
    const body = reward;
    console.log("body", body)
    return firstValueFrom(this.httpClient.put<Reward>(`${API_URL}/api/task/reward`, body, { headers }))
  }

  //DONE
  editReward(editedreward: EditedReward): Promise<any> {
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8')
    const body = editedreward;
    console.log("edit PUT update /api/task/reward/update: ", editedreward)
    return firstValueFrom(this.httpClient.post<Reward>(`${API_URL}/api/task/reward/update`, body, { headers }))
  }

  //DONE
  deleteReward(reward: Reward): Promise<any> {
    console.log("delete POST update /api/task/reward: ", reward)
    const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8')
    const body = reward.rewardid;
    return firstValueFrom(this.httpClient.post<Reward>(`${API_URL}/api/task/reward`, body, { headers }));

  }


  // doing crud in batches, not sure if this will cause issues...
  // every time there's add task this will trigger, which is not optimised
  //let's see if it'll be too heavy...
  //TODO
  //api/tasks/{userId}
  // postAllTasks() {
  //   //grab all tasks from localstorage and post to backend
  //   const headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8')
  //   //we should use the jwt token decode here to post all tasks, but uhh let's pretend we have jwt
  //   //TODO
  //   const testuser = {
  //     userId: "12345678",
  //     username: "",
  //     roles: "player" //"player", "moderator"
  //   }
  //   const task = {
  //     habits: JSON.parse(localStorage.getItem("habits") || "{}"),
  //     dailies: JSON.parse(localStorage.getItem("dailies") || "{}"),
  //     todos: JSON.parse(localStorage.getItem("todos") || "{}"),
  //     rewards: JSON.parse(localStorage.getItem("rewards") || "{}"),
  //   }
  //   const body = JSON.stringify(task);
  //   return firstValueFrom(this.httpClient.post<Task>("/api/task/habit/" + testuser.userId, body.toString(), { headers }));

  // }


}
