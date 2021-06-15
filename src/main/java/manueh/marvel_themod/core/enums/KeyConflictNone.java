package manueh.marvel_themod.core.enums;

import net.minecraftforge.client.settings.IKeyConflictContext;

public enum KeyConflictNone implements IKeyConflictContext {

    NONE {
        @Override
        public boolean isActive() {
            return true;
        }

        @Override
        public boolean conflicts(IKeyConflictContext other) {
            return false;
        }
    }


}
