## Loot Case Opening

Adds a case-opening-style reel animation that plays when you receive loot from supported content. Instead of the item just appearing in your inventory, a reel spins across a row of possible items before landing on the one you actually got.

Behavior differs slightly by content:
- **Theatre of Blood, Chambers of Xeric, Tombs of Amascut, and Doom** only spin when you receive a unique drop
- **All other supported content** spins on every completion, including common loot

In some content the game already shows you that there is a unique by recoloring the chest, hence in those scenarios the plugin only spins for unique loot.

### Supported content
- Theatre of Blood
- Chambers of Xeric
- Tombs of Amascut
- Doom of Mokhaiotl
- Corrupted Gauntlet/Gauntlet
- Moons of Peril
- Barrows
- Hallowed Sepulchre (Floor 5)
- Elven Crystal Chest
- Moon key Chest
- Larran's Big Chest (Disabled by default, enable in config)
- Zombie Pirate Locker (Disabled by default, enable in config)

### How it works
- Triggers automatically based on the rules above
- Hides the in-game reward pop-up until the spin and reveal have finished
- Press ESC at any time to skip the spin and jump straight to the result
- Press ESC again to close the result panel
- The colors of the different rarities can be adjusted in the config settings

<img width="800" height="495" alt="cox-unique" src="https://github.com/user-attachments/assets/e8829730-81fc-4ca1-8e97-5a6f8aa51701" />

<img width="800" height="494" alt="barrows-unique" src="https://github.com/user-attachments/assets/62f285db-994c-4fc0-98b8-804c0f2bb4c5" />

