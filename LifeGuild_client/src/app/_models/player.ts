//whether these tasks are completed is stored in mongoDB at the end of each day
//they decay after a while

export interface Habit {
  userid: number;
  habitid: number;
  title: string;
  isGoodorBadHabit: string; //good, bad, both
  difficulty: string;
  positiveCount: number; //counts how many times you did positively
  negativeCount: number; //counts how many times you did negatively
  dateCreated: string;
}

export interface EditHabit extends Habit {
  arrayid: number;
}

export interface ToDo {
  userid: number;
  todoid: number;
  title: string;
  difficulty: string;
  dueDate: Date; //yyyy-mm-dd. sorted nearest time at the top. new ones stay at to
  priority: string; //high med low, sorted nearest time first then priority
  isComplete: boolean;
  dateCreated: string;
}
export interface EditToDo extends ToDo {
  arrayid: number;
}

export interface Reward {
  userid: number;
  rewardid: number;
  title: string;
  cost: number;
  dateCreated: string;
}

export interface EditedReward extends Reward {
  arrayid: number;
}

export interface Daily {
  userid: number;
  dailyid: number;
  title: string;
  difficulty: string;
  isComplete: boolean; //resets everyday
  dateCreated: string;
}
export interface EditDaily extends Daily {
  arrayid: number;
}

//for simple task only crud
export interface Task {
  habits: Habit[];
  dailies: Daily[];
  toDos: ToDo[];
  rewards: Reward[];
}

//for login inits
export interface PlayerSession extends User {
  habits: Habit[];
  dailies: Daily[];
  toDos: ToDo[];
  rewards: Reward[];
  character: Character;
  pets: PetInstance;
}

export interface User {
  userid: string;
  username: string;
  roles: string[]; //"player", "moderator"
}


export interface RegisterUserDetail {
  firstname: string
  lastname: string
  email: string
  username: string
  password: string
  userrole: string
  acceptterms: boolean
  isgooglelogin: boolean
}


export interface Character {
  userid: number
  characterid: number
  // charactername: string
  health: number
  // novicelevel: number
  // readerlevel: number
  // characterlevel: number
  // writerlevel: number
  coinwallet: number
  // gachacurrency: number
  currentpetid: number
  // unlockedpets: string //use string.charAt(0)
  // currentweaponid: string
  // unlockedweapons: string
  image: string
  // dateCreated: string
}

//pets heal you. How much they heal depends on their species
//and how long they have been with the player (Bond)
//algo for their healing: Average HP per heal = (Raw heal) + (. 25 x Bond) rounded up
export interface PetInstance {
  petId: number;
  userid: number;
  image: string;
  // species: string; //dragon, unicorn, wolf, mecha, cat, dog, tiger
  healing: number; //raw heal: 7, 6, 5, 7, 3, 3, 5HP
  // arrivalDate: Date; //the date you got them. every week +1bond, resets when traded to another player
}

//for time sake we're using lesser variables for now
export interface PetBlueprint {
  petId: string; //the only diff from petInstance
  userid: number;
  image: string;
  // species: string;
  healing: number;
}

export interface TransactionPetAdoptionDetails {
  fromUserId: number
  toUserId: number
  petId: number
  amount: number
}
