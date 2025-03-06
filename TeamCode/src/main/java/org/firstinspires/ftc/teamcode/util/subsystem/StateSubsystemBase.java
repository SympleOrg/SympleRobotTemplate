package org.firstinspires.ftc.teamcode.util.subsystem;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.Subsystem;
import com.arcrobotics.ftclib.command.SubsystemBase;

public abstract class StateSubsystemBase<T extends StateSubsystemBase.StateBase<?>> extends SubsystemBase {
    protected T state;

    protected StateSubsystemBase(T defaultState) {
        this.state = defaultState;
    }

    public final Command goToState(T state, Subsystem... requirements) {
        this.setState(state);
        return this.getChangeStateCommand(state, requirements);
    }

    protected abstract Command getChangeStateCommand(T state, Subsystem... requirements);

    protected void setState(T state) {
        this.state = state;
    }

    public T getCurrentState() {
        return state;
    }

    public Command toggleStates(T state1, T state2, Subsystem... requirements) {
        return new ConditionalCommand(
                this.goToState(state1, requirements),
                this.goToState(state2, requirements),
                () -> this.getCurrentState() != state1
        );
    }

    @FunctionalInterface
    public interface StateBase<U> {
        U getUnit();
    }
}
