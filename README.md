NoLoreRepair is a repair plugin that works similarly to the /repair command found in Essentials. It allows a player with the permission to repair any item as long as it does not have a specific Lore written in the config.yml. For example, if the server owner specifies the following sentence in the lore field: "Bought from the server store", then all items with that lore (like in the picture below) will be denied the ability to be repaired.

![](http://i.imgur.com/cek4tqU.png?1)

If you like my work, please consider donating, I would greatly appreciate it. [![Donate!](https://www.paypalobjects.com/en_US/i/btn/btn_donate_LG.gif)](https://www.paypal.com/cgi-bin/webscr?cmd=_donations&business=vik1395lp@gmail.com&lc=US&item_name=Spigot%20Plugins&item_number=LegitPlay.net%20Plugin%20Dev&no_note=0&currency_code=USD&bn=PP-DonationsBF:btn_donateCC_LG.gif:NonHostedGuest)

***Permissions***

`repair.nolore` - Allows you to repair items as long as they don't have the specified lore.

`repair.admin` - Bypasses the Time Delay and Blacklisted Lores.

`norepair.nolore` - Denies repairing items with lore on an anvil without giving the player permission to use /repair command.

***Commands***

`/repair all` - Repairs all repairable items in the player's inventory, ignoreing items with the blacklisted lore.

`/repair hand` - Repairs the item held by the player, given that it isnt a repairable item or has the blacklisted lore.

`/repair` - Works same as /repair hand.

***Aliases:***

    /repair
    /rp
    /fix

***Config***

The config.yml file looks similar to this:

    Block All Lores: False
    # Set this to True if you want to block any item that has a lore from being repaired.
    
    Lores: Bought from the server store;This item cannot be repaired
    # Type the lores that you want to be blocked. use a semicolon(;) to separate each lore.
    
    Block Anvil Repair: False
    # Set this to True if you want to block items from being repaired by an anvil as well.
    
    Message: This item is not allowed to be repaired.
    # Message sent to the player if he/she tries to repair an item with the specified lore.
    
    Delay: 60
    # Please give the delay (in minutes) before the /repair command cam be used again.

Here is an example of how the plugin works/is used:

***/repair all command:***

![](http://i.imgur.com/qHdYWrh.png?1)

If there are no items that can be repaired:

![](http://i.imgur.com/sRogshO.png?2)

***/repair hand command:***

![](http://i.imgur.com/rXFJNVR.png?1)

Unrepairable Item:

![](http://i.imgur.com/ySz3uLk.png?1)

***/repair hand command on an item with blacklisted lore:***

![](http://i.imgur.com/lvkpaN0.png?1)


This plugin is licensed under **CC Attribution-NonCommercial-ShareAlike 4.0 International**. In very basic terms, Do whatever you want with the code of this plugin, as long as you give credits to the author and/or the plugin itself.

Please leave a comment or rate the plugin, and tell me what you think of it. I would really appreciate it.
