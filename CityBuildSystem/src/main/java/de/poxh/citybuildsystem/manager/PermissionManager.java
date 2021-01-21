package de.cb.poxh.manager;

import de.dytanic.cloudnet.CloudNet;
import de.dytanic.cloudnet.driver.CloudNetDriver;
import de.dytanic.cloudnet.driver.event.events.permission.PermissionAddGroupEvent;
import de.dytanic.cloudnet.driver.permission.IPermissionManagement;
import de.dytanic.cloudnet.driver.permission.IPermissionUser;
import de.dytanic.cloudnet.driver.permission.PermissionGroup;
import de.dytanic.cloudnet.driver.permission.PermissionUserGroupInfo;
import org.bukkit.entity.Player;

import java.util.concurrent.TimeUnit;

public class PermissionManager {

    public void addPermission(Player player) {
        IPermissionUser permissionUser = CloudNetDriver.getInstance().getPermissionManagement().getUser(player.getUniqueId());

        if (permissionUser == null) {
            return;
        }

        permissionUser.addPermission("minecraft.command.gamemode", true); //adds a permission
        permissionUser.addPermission("CityBuild", "minecraft.command.difficulty"); //adds a permission which the effect works only on CityBuild group services
        CloudNetDriver.getInstance().getPermissionManagement().updateUser(permissionUser);
    }

    public void removePermission(Player player) {
        IPermissionUser permissionUser = CloudNetDriver.getInstance().getPermissionManagement().getUser(player.getUniqueId());

        if (permissionUser == null) {
            return;
        }

        CloudNetDriver.getInstance().getPermissionManagement().getGroup(player.getName());

        permissionUser.removePermission("minecraft.command.gamemode"); //removes a permission
        permissionUser.removePermission("CityBuild", "minecraft.command.difficulty"); //removes a group specific permission
        CloudNetDriver.getInstance().getPermissionManagement().updateUser(permissionUser);
    }

    public void addGroup(Player player) {
        IPermissionUser permissionUser = CloudNetDriver.getInstance().getPermissionManagement().getUser(player.getUniqueId());

        if (permissionUser == null) {
            return;
        }

        permissionUser.addGroup("Admin"); //add Admin group
        permissionUser.addGroup("YouTuber", 5, TimeUnit.DAYS); //add YouTuber group for 5 days
        CloudNetDriver.getInstance().getPermissionManagement().updateUser(permissionUser);

        if (permissionUser.inGroup("Admin")) {
            player.sendMessage("Your in group Admin!");
        }
    }

    public void removeGroup(Player player) {
        IPermissionUser permissionUser = CloudNetDriver.getInstance().getPermissionManagement().getUser(player.getUniqueId());

        if (permissionUser == null) {
            return;
        }

        permissionUser.removeGroup("YouTuber"); //removes the YouTuber group
        CloudNetDriver.getInstance().getPermissionManagement().updateUser(permissionUser);
    }
}


