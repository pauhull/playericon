package de.pauhull.playericon.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.net.InetAddress;
import java.util.UUID;

@AllArgsConstructor
public class IconPlayer {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private UUID uuid;

    @Getter
    @Setter
    private InetAddress address;

}
