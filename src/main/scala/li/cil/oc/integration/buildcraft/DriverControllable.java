package li.cil.oc.integration.buildcraft;

import buildcraft.api.tiles.IControllable;
import li.cil.oc.api.machine.Arguments;
import li.cil.oc.api.machine.Callback;
import li.cil.oc.api.machine.Context;
import li.cil.oc.api.network.ManagedEnvironment;
import li.cil.oc.api.prefab.DriverTileEntity;
import li.cil.oc.integration.ManagedTileEntityEnvironment;
import net.minecraft.world.World;

public final class DriverControllable extends DriverTileEntity {
    @Override
    public Class<?> getTileEntityClass() {
        return IControllable.class;
    }

    @Override
    public ManagedEnvironment createEnvironment(World world, int x, int y, int z) {
        return new Environment((IControllable) world.getTileEntity(x, y, z));
    }

    public static final class Environment extends ManagedTileEntityEnvironment<IControllable> {
        public Environment(final IControllable tileEntity) {
            super(tileEntity, "bc_controllable");
        }

        @Callback(doc = "function():string -- Get the current control mode.")
        public Object[] getControlMode(final Context context, final Arguments args) {
            return new Object[]{tileEntity.getControlMode().name()};
        }

        @Callback(doc = "function(mode:string):boolean -- Test whether the specified control mode is acceptable.")
        public Object[] acceptsControlMode(final Context context, final Arguments args) {
            return new Object[]{tileEntity.acceptsControlMode(IControllable.Mode.valueOf(args.checkString(0)))};
        }

        @Callback(doc = "function(mode:string):boolean -- Sets the control mode to the specified value.")
        public Object[] setControlMode(final Context context, final Arguments args) {
            tileEntity.setControlMode(IControllable.Mode.valueOf(args.checkString(0)));
            return null;
        }
    }
}
