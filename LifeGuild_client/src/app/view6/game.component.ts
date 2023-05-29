import { Component } from '@angular/core';

interface Game {
  title: string;
  externalUrl: string;
  imgUrl: string;
  description: string;
}
@Component({
  selector: 'app-game',
  templateUrl: './game.component.html',
  styleUrls: ['./game.component.css']
})
export class GameComponent {
  placeholderImg = '';
  activeGame = { images: 'https://cdn.akamai.steamstatic.com/steam/apps/257850/header.jpg?t=1649868127' }
  featuredGames = [
    {
      images: 'https://cdn.akamai.steamstatic.com/steam/apps/504230/header.jpg?t=1684953953'
    },
    {
      images: 'https://cdn.akamai.steamstatic.com/steam/apps/391540/header.jpg?t=1579096091'
    },
    {
      images: 'https://cdn.akamai.steamstatic.com/steam/apps/115800/header.jpg?t=1659086154'
    }
  ]

  gameList: Game[] = [
    {
      title: 'UnderTale',
      externalUrl: 'https://store.steampowered.com/app/391540/Undertale/',
      imgUrl: 'https://cdn.akamai.steamstatic.com/steam/apps/391540/header.jpg?t=1579096091',
      description: "UNDERTALE! The RPG game where you don't have to destroy anyone. "
    },
    {
      title: 'Owlboy',
      externalUrl: 'https://store.steampowered.com/app/115800/Owlboy/',
      imgUrl: 'https://cdn.akamai.steamstatic.com/steam/apps/115800/header.jpg?t=1659086154',
      description: "Owlboy is a story-driven platform adventure game, where you can fly and explore a brand new world in the clouds! Pick up your friends, and bring them with you as you explore the open skies, in one of the most detailed adventures of this era.  "
    },
    {
      title: 'Celeste',
      externalUrl: 'https://store.steampowered.com/app/504230/Celeste/',
      imgUrl: 'https://cdn.akamai.steamstatic.com/steam/apps/504230/header.jpg?t=1684953953',
      description: "Help Madeline survive her inner demons on her journey to the top of Celeste Mountain, in this super-tight platformer from the creators of TowerFall. Brave hundreds of hand-crafted challenges, uncover devious secrets, and piece together the mystery of the mountain.  "
    },
    {
      title: 'Touhou Luna Nights',
      externalUrl: 'https://store.steampowered.com/app/851100/Touhou_Luna_Nights/',
      imgUrl: 'https://cdn.akamai.steamstatic.com/steam/apps/851100/header.jpg?t=1671415244',
      description: "Touhou Luna Nights is a Metroidvania title with a heavy emphasis on exploration and action.　 Developed by Team Ladybug, creators of multiple fantastic action games thus far. "
    },
    {
      title: 'Katana Zero',
      externalUrl: 'https://store.steampowered.com/app/460950/Katana_ZERO/',
      imgUrl: 'https://cdn.akamai.steamstatic.com/steam/apps/460950/header.jpg?t=1666986801',
      description: "Katana ZERO is a stylish neo-noir, action-platformer featuring breakneck action and instant-death combat. Slash, dash, and manipulate time to unravel your past in a beautifully brutal acrobatic display.  "
    },
    {
      title: 'Little Witch in the Woods',
      externalUrl: 'https://store.steampowered.com/app/1594940/Little_Witch_in_the_Woods/',
      imgUrl: 'https://cdn.akamai.steamstatic.com/steam/apps/1594940/header.jpg?t=1669166511',
      description: "Little Witch in the Woods tells the story of Ellie, an apprentice witch. Explore the mystical forest, help the charming residents, and experience the daily life of the witch.  "
    },
    {
      title: 'Enter the Gungeon',
      externalUrl: 'https://store.steampowered.com/app/311690/Enter_the_Gungeon/',
      imgUrl: 'https://cdn.akamai.steamstatic.com/steam/apps/311690/header.jpg?t=1681773275',
      description: "Enter the Gungeon is a bullet hell dungeon crawler following a band of misfits seeking to shoot, loot, dodge roll and table-flip their way to personal absolution by reaching the legendary Gungeon’s ultimate treasure: the gun that can kill the past.  "
    },
    {
      title: 'Enter the Gungeon',
      externalUrl: 'https://store.steampowered.com/app/311690/Enter_the_Gungeon/',
      imgUrl: 'https://cdn.akamai.steamstatic.com/steam/apps/311690/header.jpg?t=1681773275',
      description: "Enter the Gungeon is a bullet hell dungeon crawler following a band of misfits seeking to shoot, loot, dodge roll and table-flip their way to personal absolution by reaching the legendary Gungeon’s ultimate treasure: the gun that can kill the past.  "
    },
    {
      title: 'Enter the Gungeon',
      externalUrl: 'https://store.steampowered.com/app/311690/Enter_the_Gungeon/',
      imgUrl: 'https://cdn.akamai.steamstatic.com/steam/apps/311690/header.jpg?t=1681773275',
      description: "Enter the Gungeon is a bullet hell dungeon crawler following a band of misfits seeking to shoot, loot, dodge roll and table-flip their way to personal absolution by reaching the legendary Gungeon’s ultimate treasure: the gun that can kill the past.  "
    },
    {
      title: 'Enter the Gungeon',
      externalUrl: 'https://store.steampowered.com/app/311690/Enter_the_Gungeon/',
      imgUrl: 'https://cdn.akamai.steamstatic.com/steam/apps/311690/header.jpg?t=1681773275',
      description: "Enter the Gungeon is a bullet hell dungeon crawler following a band of misfits seeking to shoot, loot, dodge roll and table-flip their way to personal absolution by reaching the legendary Gungeon’s ultimate treasure: the gun that can kill the past.  "
    },
    {
      title: 'Enter the Gungeon',
      externalUrl: 'https://store.steampowered.com/app/311690/Enter_the_Gungeon/',
      imgUrl: 'https://cdn.akamai.steamstatic.com/steam/apps/311690/header.jpg?t=1681773275',
      description: "Enter the Gungeon is a bullet hell dungeon crawler following a band of misfits seeking to shoot, loot, dodge roll and table-flip their way to personal absolution by reaching the legendary Gungeon’s ultimate treasure: the gun that can kill the past.  "
    },
    {
      title: 'Enter the Gungeon',
      externalUrl: 'https://store.steampowered.com/app/311690/Enter_the_Gungeon/',
      imgUrl: 'https://cdn.akamai.steamstatic.com/steam/apps/311690/header.jpg?t=1681773275',
      description: "Enter the Gungeon is a bullet hell dungeon crawler following a band of misfits seeking to shoot, loot, dodge roll and table-flip their way to personal absolution by reaching the legendary Gungeon’s ultimate treasure: the gun that can kill the past.  "
    },
    {
      title: 'Enter the Gungeon',
      externalUrl: 'https://store.steampowered.com/app/311690/Enter_the_Gungeon/',
      imgUrl: 'https://cdn.akamai.steamstatic.com/steam/apps/311690/header.jpg?t=1681773275',
      description: "Enter the Gungeon is a bullet hell dungeon crawler following a band of misfits seeking to shoot, loot, dodge roll and table-flip their way to personal absolution by reaching the legendary Gungeon’s ultimate treasure: the gun that can kill the past.  "
    },
    {
      title: 'Chained Echoes',
      externalUrl: 'https://store.steampowered.com/app/1229240/Chained_Echoes/',
      imgUrl: 'https://cdn.akamai.steamstatic.com/steam/apps/1229240/header.jpg?t=1678106388',
      description: "Take up your sword, channel your magic or board your Mech. Chained Echoes is a 16-bit style RPG set in a fantasy world where dragons are as common as piloted mechanical suits.   "
    },
    {
      title: 'Enter the Gungeon',
      externalUrl: 'https://store.steampowered.com/app/311690/Enter_the_Gungeon/',
      imgUrl: 'https://cdn.akamai.steamstatic.com/steam/apps/311690/header.jpg?t=1681773275',
      description: "Enter the Gungeon is a bullet hell dungeon crawler following a band of misfits seeking to shoot, loot, dodge roll and table-flip their way to personal absolution by reaching the legendary Gungeon’s ultimate treasure: the gun that can kill the past.  "
    },
    {
      title: 'Enter the Gungeon',
      externalUrl: 'https://store.steampowered.com/app/311690/Enter_the_Gungeon/',
      imgUrl: 'https://cdn.akamai.steamstatic.com/steam/apps/311690/header.jpg?t=1681773275',
      description: "Enter the Gungeon is a bullet hell dungeon crawler following a band of misfits seeking to shoot, loot, dodge roll and table-flip their way to personal absolution by reaching the legendary Gungeon’s ultimate treasure: the gun that can kill the past.  "
    },
    {
      title: 'Enter the Gungeon',
      externalUrl: 'https://store.steampowered.com/app/311690/Enter_the_Gungeon/',
      imgUrl: 'https://cdn.akamai.steamstatic.com/steam/apps/311690/header.jpg?t=1681773275',
      description: "Enter the Gungeon is a bullet hell dungeon crawler following a band of misfits seeking to shoot, loot, dodge roll and table-flip their way to personal absolution by reaching the legendary Gungeon’s ultimate treasure: the gun that can kill the past.  "
    },
    {
      title: 'Enter the Gungeon',
      externalUrl: 'https://store.steampowered.com/app/311690/Enter_the_Gungeon/',
      imgUrl: 'https://cdn.akamai.steamstatic.com/steam/apps/311690/header.jpg?t=1681773275',
      description: "Enter the Gungeon is a bullet hell dungeon crawler following a band of misfits seeking to shoot, loot, dodge roll and table-flip their way to personal absolution by reaching the legendary Gungeon’s ultimate treasure: the gun that can kill the past.  "
    },
  ];
}
