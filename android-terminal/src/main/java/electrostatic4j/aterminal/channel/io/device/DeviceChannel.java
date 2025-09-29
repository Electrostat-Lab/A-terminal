package electrostatic4j.aterminal.channel.io.device;

import electrostatic4j.aterminal.channel.PlatformChannel;
import electrostatic4j.aterminal.channel.ShellChannel;
import electrostatic4j.aterminal.channel.VFSChannel;

public class DeviceChannel implements PlatformChannel {
    private ShellChannel shellChannel;
    private VFSChannel vfsChannel;
}
