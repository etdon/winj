package com.etdon.winj.facade.assembler;

import com.etdon.commons.builder.FluentBuilder;
import org.jetbrains.annotations.NotNull;

public final class Prefix {

    private Prefix() {

        throw new UnsupportedOperationException();

    }

    public static Builder builder() {

        return new Builder();

    }

    public static final class Builder implements FluentBuilder<Byte> {

        // W, 64-bit operand size, determination by CS.D otherwise.
        private boolean wideMode;
        // R, Extends the ModR/M reg field.
        private boolean rmRegExtension;
        // X, extends the SIB index field.
        private boolean sibIndexExtension;
        // B, extends either ModR/M r/m field, SIB base field or Opcode reg field.
        private boolean flexExtension;

        private Builder() {

        }

        public Builder wideMode(final boolean wideMode) {

            this.wideMode = wideMode;
            return this;

        }

        public Builder w(final boolean w) {

            this.wideMode = w;
            return this;

        }

        public Builder rmRegExtension(final boolean rmRegExtension) {

            this.rmRegExtension = rmRegExtension;
            return this;

        }

        public Builder r(final boolean r) {

            this.rmRegExtension = r;
            return this;

        }

        public Builder sibIndexExtension(final boolean sibIndexExtension) {

            this.sibIndexExtension = sibIndexExtension;
            return this;

        }

        public Builder x(final boolean x) {

            this.sibIndexExtension = x;
            return this;

        }

        public Builder flexExtension(final boolean flexExtension) {

            this.flexExtension = flexExtension;
            return this;

        }

        public Builder b(final boolean b) {

            this.flexExtension = b;
            return this;

        }

        @NotNull
        @Override
        public Byte build() {

            return (byte) (Opcodes.Prefix.REX |
                    (this.wideMode ? 0b1000 : 0) |
                    (this.rmRegExtension ? 0b0100 : 0) |
                    (this.sibIndexExtension ? 0b0010 : 0) |
                    (this.flexExtension ? 0b0001 : 0));

        }

    }

}
