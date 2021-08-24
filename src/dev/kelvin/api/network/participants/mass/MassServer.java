package dev.kelvin.api.network.participants.mass;

import dev.kelvin.api.HighLevelNetworkAPI;
import dev.kelvin.api.network.NetworkParticipant;

public class MassServer extends NetworkParticipant {

    public MassServer(Object object, HighLevelNetworkAPI hln) {
        super(object, hln);
    }

    @Override
    public void rcu(String methodName, String... args) {

    }

    @Override
    public void rcu_id(int uuid, String methodName, String... args) {

    }

    @Override
    public void rct(String methodName, String... args) {

    }

    @Override
    public void rct_id(int uuid, String methodName, String... args) {

    }

    @Override
    protected void listenUdp() {

    }

    @Override
    protected void listenTcp() {

    }

}
