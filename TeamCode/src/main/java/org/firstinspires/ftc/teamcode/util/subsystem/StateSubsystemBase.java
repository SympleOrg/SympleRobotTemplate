package org.firstinspires.ftc.teamcode.util.subsystem;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.ConditionalCommand;
import com.arcrobotics.ftclib.command.Subsystem;
import com.arcrobotics.ftclib.command.SubsystemBase;

/**
 * A base class for subsystems that operate in discrete states.
 *
 * <p>This class is designed to integrate with the Command-based framework.
 * Each subsystem has a current {@link StateBase} that represents its mode,
 * and commands can be generated to transition between states.</p>
 *
 * <p>Example usage:</p>
 * <pre> {@code
 * public class ClawSubsystem extends StateSubsystemBase<ClawState> {
 *
 *     public ClawSubsystem() {
 *         super(ClawState.CLOSED);
 *     }
 *
 *     @Override
 *     protected Command getChangeStateCommand(ClawState state, Subsystem... requirements) {
 *         return new InstantCommand(() -> {
 *             // hardware code to set claw position
 *         }, requirements);
 *     }
 *
 *     public enum ClawState implements StateBase<String> {
 *         OPEN("Open"),
 *         CLOSED("Closed");
 *
 *         private final String label;
 *         ClawState(String label) { this.label = label; }
 *         public String getUnit() { return label; }
 *     }
 * }
 * }</pre>
 *
 * @param <T> the type of state, which must implement {@link StateBase}
 */
public abstract class StateSubsystemBase<T extends StateSubsystemBase.StateBase<?>> extends SubsystemBase {
    /**
     * The current state of the subsystem.
     */
    protected T state;

    /**
     * Creates a new subsystem with a given default state.
     *
     * @param defaultState the initial state for this subsystem
     */
    protected StateSubsystemBase(T defaultState) {
        this.state = defaultState;
    }

    /**
     * Transitions the subsystem to a new state and returns a command
     * representing that change.
     *
     * @param state        the target state
     * @param requirements any subsystem requirements for the command
     * @return a command that applies the state transition
     */
    public final Command goToState(T state, Subsystem... requirements) {
        this.setState(state);
        return this.getChangeStateCommand(state, requirements);
    }

    /**
     * Creates a command to handle the transition into the given state.
     * <p>This method must be implemented by subclasses to define
     * the actual behavior of switching states.</p>
     *
     * @param state        the target state
     * @param requirements any subsystem requirements for the command
     * @return a command that applies the state transition
     */
    protected abstract Command getChangeStateCommand(T state, Subsystem... requirements);

    /**
     * Updates the current state of the subsystem.
     * Does not automatically execute any hardware actions.
     *
     * @param state the new state
     */
    protected void setState(T state) {
        this.state = state;
    }

    /**
     * Returns the subsystem's current state.
     *
     * @return the current state
     */
    public T getCurrentState() {
        return state;
    }

    /**
     * Toggles between two states. If the current state equals {@code state1},
     * it will transition to {@code state2}, otherwise it will transition to {@code state1}.
     *
     * @param state1       the first state
     * @param state2       the second state
     * @param requirements any subsystem requirements for the command
     * @return a conditional command that switches between the given states
     */
    public Command toggleStates(T state1, T state2, Subsystem... requirements) {
        return new ConditionalCommand(
                this.goToState(state1, requirements),
                this.goToState(state2, requirements),
                () -> this.getCurrentState() != state1
        );
    }

    protected Subsystem[] mergeSubsystemLists(Subsystem[] original, Subsystem... toMerge) {
        Subsystem[] result = new Subsystem[original.length + toMerge.length];
        System.arraycopy(toMerge, 0, result, 0, toMerge.length);
        System.arraycopy(original, 0, result, toMerge.length, original.length);
        return result;
    }

    /**
     * A functional interface representing a valid state for a subsystem.
     * <p>States may wrap arbitrary values (such as enums, strings, or constants)
     * that define subsystem behavior.</p>
     *
     * @param <U> the type of unit associated with the state
     */
    @FunctionalInterface
    public interface StateBase<U> {
        U getUnit();
    }
}
