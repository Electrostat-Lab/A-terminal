package electrostatic4j.aterminal.channel.io.bus;

import electrostatic4j.aterminal.channel.PlatformChannel;
import electrostatic4j.aterminal.channel.ShellChannel;
import electrostatic4j.aterminal.channel.VFSChannel;

public class BusProtocolChannel implements PlatformChannel {
    private ShellChannel shellChannel;
    private VFSChannel vfsChannel;
}
