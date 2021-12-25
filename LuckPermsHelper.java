package net.mov51.periderm;

import net.luckperms.api.cacheddata.CachedMetaData;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.matcher.NodeMatcher;
import net.luckperms.api.node.types.MetaNode;
import org.bukkit.entity.Player;

import static net.mov51.neworigin.NewOrigin.LPapi;

public class LuckPermsHelper {

    public static String getMetaValue(Player p, AspenMetaKey metaKey){
        // obtain CachedMetaData - the easiest way is via the PlayerAdapter
        // of course, you can get it via a User too if the player is offline.
        CachedMetaData metaData = LPapi.getPlayerAdapter(Player.class).getMetaData(p);

        // query & parse the meta value
        return metaData.getMetaValue(metaKey.key);
    }

    public static void setMetaValue(Player p, AspenMetaKey metaKey, String value){
        // obtain a User instance (by any means! see above for other ways)
        User user = LPapi.getPlayerAdapter(Player.class).getUser(p);

        // create a new MetaNode holding the level value
        // of course, this can have context/expiry/etc. too!
        MetaNode node = MetaNode.builder(metaKey.key, value).build();

        // clear any existing meta nodes with the same key - we want to override
        user.data().clear(NodeMatcher.metaKey(metaKey.key));
        // add the new node
        user.data().add(node);

        // save!
        LPapi.getUserManager().saveUser(user);
    }

}
