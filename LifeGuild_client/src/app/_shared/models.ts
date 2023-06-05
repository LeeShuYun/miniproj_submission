//userid will be in localStorage when logged in
export interface Player {
  userid: string;
  characterid: number;
  health: number
  coinwallet: number;
  currentpetid: number
  image: string
  isGameSectionLocked: boolean;
  petimage: string;
  pethealing: number;
  enemyhealth: number;
  enemyimage: string;
}

