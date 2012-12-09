package net.minecraft.server;

public abstract class NetHandler
{
    /**
     * determine if it is a server handler
     */
    public abstract boolean a();

    /**
     * Handle Packet51MapChunk (full chunk update of blocks, metadata, light levels, and optionally biome data)
     */
    public void a(Packet51MapChunk par1Packet51MapChunk) {}

    /**
     * Default handler called for packets that don't have their own handlers in NetServerHandler; kicks player from the
     * server.
     */
    public void onUnhandledPacket(Packet par1Packet) {}

    public void a(String par1Str, Object[] par2ArrayOfObj) {}

    public void a(Packet255KickDisconnect par1Packet255KickDisconnect)
    {
        this.onUnhandledPacket(par1Packet255KickDisconnect);
    }

    public void a(Packet1Login par1Packet1Login)
    {
        this.onUnhandledPacket(par1Packet1Login);
    }

    public void a(Packet10Flying par1Packet10Flying)
    {
        this.onUnhandledPacket(par1Packet10Flying);
    }

    public void a(Packet52MultiBlockChange par1Packet52MultiBlockChange)
    {
        this.onUnhandledPacket(par1Packet52MultiBlockChange);
    }

    public void a(Packet14BlockDig par1Packet14BlockDig)
    {
        this.onUnhandledPacket(par1Packet14BlockDig);
    }

    public void a(Packet53BlockChange par1Packet53BlockChange)
    {
        this.onUnhandledPacket(par1Packet53BlockChange);
    }

    public void a(Packet20NamedEntitySpawn par1Packet20NamedEntitySpawn)
    {
        this.onUnhandledPacket(par1Packet20NamedEntitySpawn);
    }

    public void a(Packet30Entity par1Packet30Entity)
    {
        this.onUnhandledPacket(par1Packet30Entity);
    }

    public void a(Packet34EntityTeleport par1Packet34EntityTeleport)
    {
        this.onUnhandledPacket(par1Packet34EntityTeleport);
    }

    public void a(Packet15Place par1Packet15Place)
    {
        this.onUnhandledPacket(par1Packet15Place);
    }

    public void a(Packet16BlockItemSwitch par1Packet16BlockItemSwitch)
    {
        this.onUnhandledPacket(par1Packet16BlockItemSwitch);
    }

    public void a(Packet29DestroyEntity par1Packet29DestroyEntity)
    {
        this.onUnhandledPacket(par1Packet29DestroyEntity);
    }

    public void a(Packet21PickupSpawn par1Packet21PickupSpawn)
    {
        this.onUnhandledPacket(par1Packet21PickupSpawn);
    }

    public void a(Packet22Collect par1Packet22Collect)
    {
        this.onUnhandledPacket(par1Packet22Collect);
    }

    public void a(Packet3Chat par1Packet3Chat)
    {
        this.onUnhandledPacket(par1Packet3Chat);
    }

    public void a(Packet23VehicleSpawn par1Packet23VehicleSpawn)
    {
        this.onUnhandledPacket(par1Packet23VehicleSpawn);
    }

    public void a(Packet18ArmAnimation par1Packet18Animation)
    {
        this.onUnhandledPacket(par1Packet18Animation);
    }

    /**
     * runs registerPacket on the given Packet19EntityAction
     */
    public void a(Packet19EntityAction par1Packet19EntityAction)
    {
        this.onUnhandledPacket(par1Packet19EntityAction);
    }

    public void a(Packet2Handshake par1Packet2ClientProtocol)
    {
        this.onUnhandledPacket(par1Packet2ClientProtocol);
    }

    public void a(Packet253KeyRequest par1Packet253ServerAuthData)
    {
        this.onUnhandledPacket(par1Packet253ServerAuthData);
    }

    public void a(Packet252KeyResponse par1Packet252SharedKey)
    {
        this.onUnhandledPacket(par1Packet252SharedKey);
    }

    public void a(Packet24MobSpawn par1Packet24MobSpawn)
    {
        this.onUnhandledPacket(par1Packet24MobSpawn);
    }

    public void a(Packet4UpdateTime par1Packet4UpdateTime)
    {
        this.onUnhandledPacket(par1Packet4UpdateTime);
    }

    public void a(Packet6SpawnPosition par1Packet6SpawnPosition)
    {
        this.onUnhandledPacket(par1Packet6SpawnPosition);
    }

    /**
     * Packet handler
     */
    public void a(Packet28EntityVelocity par1Packet28EntityVelocity)
    {
        this.onUnhandledPacket(par1Packet28EntityVelocity);
    }

    /**
     * Packet handler
     */
    public void a(Packet40EntityMetadata par1Packet40EntityMetadata)
    {
        this.onUnhandledPacket(par1Packet40EntityMetadata);
    }

    /**
     * Packet handler
     */
    public void a(Packet39AttachEntity par1Packet39AttachEntity)
    {
        this.onUnhandledPacket(par1Packet39AttachEntity);
    }

    public void a(Packet7UseEntity par1Packet7UseEntity)
    {
        this.onUnhandledPacket(par1Packet7UseEntity);
    }

    /**
     * Packet handler
     */
    public void a(Packet38EntityStatus par1Packet38EntityStatus)
    {
        this.onUnhandledPacket(par1Packet38EntityStatus);
    }

    /**
     * Recieves player health from the server and then proceeds to set it locally on the client.
     */
    public void a(Packet8UpdateHealth par1Packet8UpdateHealth)
    {
        this.onUnhandledPacket(par1Packet8UpdateHealth);
    }

    /**
     * respawns the player
     */
    public void a(Packet9Respawn par1Packet9Respawn)
    {
        this.onUnhandledPacket(par1Packet9Respawn);
    }

    public void a(Packet60Explosion par1Packet60Explosion)
    {
        this.onUnhandledPacket(par1Packet60Explosion);
    }

    public void a(Packet100OpenWindow par1Packet100OpenWindow)
    {
        this.onUnhandledPacket(par1Packet100OpenWindow);
    }

    public void handleContainerClose(Packet101CloseWindow par1Packet101CloseWindow)
    {
        this.onUnhandledPacket(par1Packet101CloseWindow);
    }

    public void a(Packet102WindowClick par1Packet102WindowClick)
    {
        this.onUnhandledPacket(par1Packet102WindowClick);
    }

    public void a(Packet103SetSlot par1Packet103SetSlot)
    {
        this.onUnhandledPacket(par1Packet103SetSlot);
    }

    public void a(Packet104WindowItems par1Packet104WindowItems)
    {
        this.onUnhandledPacket(par1Packet104WindowItems);
    }

    /**
     * Updates Client side signs
     */
    public void a(Packet130UpdateSign par1Packet130UpdateSign)
    {
        this.onUnhandledPacket(par1Packet130UpdateSign);
    }

    public void a(Packet105CraftProgressBar par1Packet105UpdateProgressbar)
    {
        this.onUnhandledPacket(par1Packet105UpdateProgressbar);
    }

    public void a(Packet5EntityEquipment par1Packet5PlayerInventory)
    {
        this.onUnhandledPacket(par1Packet5PlayerInventory);
    }

    public void a(Packet106Transaction par1Packet106Transaction)
    {
        this.onUnhandledPacket(par1Packet106Transaction);
    }

    /**
     * Packet handler
     */
    public void a(Packet25EntityPainting par1Packet25EntityPainting)
    {
        this.onUnhandledPacket(par1Packet25EntityPainting);
    }

    public void a(Packet54PlayNoteBlock par1Packet54PlayNoteBlock)
    {
        this.onUnhandledPacket(par1Packet54PlayNoteBlock);
    }

    /**
     * runs registerPacket on the given Packet200Statistic
     */
    public void a(Packet200Statistic par1Packet200Statistic)
    {
        this.onUnhandledPacket(par1Packet200Statistic);
    }

    public void a(Packet17EntityLocationAction par1Packet17Sleep)
    {
        this.onUnhandledPacket(par1Packet17Sleep);
    }

    public void a(Packet70Bed par1Packet70GameEvent)
    {
        this.onUnhandledPacket(par1Packet70GameEvent);
    }

    /**
     * Handles weather packet
     */
    public void a(Packet71Weather par1Packet71Weather)
    {
        this.onUnhandledPacket(par1Packet71Weather);
    }

    /**
     * Contains logic for handling packets containing arbitrary unique item data. Currently this is only for maps.
     */
    public void a(Packet131ItemData par1Packet131MapData)
    {
        this.onUnhandledPacket(par1Packet131MapData);
    }

    public void a(Packet61WorldEvent par1Packet61DoorChange)
    {
        this.onUnhandledPacket(par1Packet61DoorChange);
    }

    /**
     * Handle a server ping packet.
     */
    public void a(Packet254GetInfo par1Packet254ServerPing)
    {
        this.onUnhandledPacket(par1Packet254ServerPing);
    }

    /**
     * Handle an entity effect packet.
     */
    public void a(Packet41MobEffect par1Packet41EntityEffect)
    {
        this.onUnhandledPacket(par1Packet41EntityEffect);
    }

    /**
     * Handle a remove entity effect packet.
     */
    public void a(Packet42RemoveMobEffect par1Packet42RemoveEntityEffect)
    {
        this.onUnhandledPacket(par1Packet42RemoveEntityEffect);
    }

    /**
     * Handle a player information packet.
     */
    public void a(Packet201PlayerInfo par1Packet201PlayerInfo)
    {
        this.onUnhandledPacket(par1Packet201PlayerInfo);
    }

    /**
     * Handle a keep alive packet.
     */
    public void a(Packet0KeepAlive par1Packet0KeepAlive)
    {
        this.onUnhandledPacket(par1Packet0KeepAlive);
    }

    /**
     * Handle an experience packet.
     */
    public void a(Packet43SetExperience par1Packet43Experience)
    {
        this.onUnhandledPacket(par1Packet43Experience);
    }

    /**
     * Handle a creative slot packet.
     */
    public void a(Packet107SetCreativeSlot par1Packet107CreativeSetSlot)
    {
        this.onUnhandledPacket(par1Packet107CreativeSetSlot);
    }

    /**
     * Handle a entity experience orb packet.
     */
    public void a(Packet26AddExpOrb par1Packet26EntityExpOrb)
    {
        this.onUnhandledPacket(par1Packet26EntityExpOrb);
    }

    public void a(Packet108ButtonClick par1Packet108EnchantItem) {}

    public void a(Packet250CustomPayload par1Packet250CustomPayload) {}

    public void a(Packet35EntityHeadRotation par1Packet35EntityHeadRotation)
    {
        this.onUnhandledPacket(par1Packet35EntityHeadRotation);
    }

    public void a(Packet132TileEntityData par1Packet132TileEntityData)
    {
        this.onUnhandledPacket(par1Packet132TileEntityData);
    }

    /**
     * Handle a player abilities packet.
     */
    public void a(Packet202Abilities par1Packet202PlayerAbilities)
    {
        this.onUnhandledPacket(par1Packet202PlayerAbilities);
    }

    public void a(Packet203TabComplete par1Packet203AutoComplete)
    {
        this.onUnhandledPacket(par1Packet203AutoComplete);
    }

    public void a(Packet204LocaleAndViewDistance par1Packet204ClientInfo)
    {
        this.onUnhandledPacket(par1Packet204ClientInfo);
    }

    public void a(Packet62NamedSoundEffect par1Packet62LevelSound)
    {
        this.onUnhandledPacket(par1Packet62LevelSound);
    }

    public void a(Packet55BlockBreakAnimation par1Packet55BlockDestroy)
    {
        this.onUnhandledPacket(par1Packet55BlockDestroy);
    }

    public void a(Packet205ClientCommand par1Packet205ClientCommand) {}

    public void a(Packet56MapChunkBulk par1Packet56MapChunks)
    {
        this.onUnhandledPacket(par1Packet56MapChunks);
    }

    /**
     * packet.processPacket is only called if this returns true
     */
    public boolean b()
    {
        return false;
    }
}
