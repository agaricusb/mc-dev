package net.minecraft.server;

class ContainerSheepBreed extends Container
{
    final EntitySheep a;

    ContainerSheepBreed(EntitySheep par1EntitySheep)
    {
        this.a = par1EntitySheep;
    }

    public boolean a(EntityHuman par1EntityPlayer)
    {
        return false;
    }
}
