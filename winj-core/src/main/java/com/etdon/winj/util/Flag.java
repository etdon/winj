package com.etdon.winj.util;

import com.etdon.commons.conditional.Preconditions;

public final class Flag {

    public static boolean check(final long flags, final long target) {

        return (flags & target) == target;

    }

    public static long combine(final long... flags) {

        Preconditions.checkState(flags.length > 0);
        long flag = flags[0];
        if (flags.length > 1) {
            for (int i = 1; i < flags.length; i++)
                flag |= flags[i];
        }

        return flag;

    }

    private Flag() {

        throw new UnsupportedOperationException();

    }

}
