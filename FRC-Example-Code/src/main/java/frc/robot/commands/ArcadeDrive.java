package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.Drivetrain;

public class ArcadeDrive extends Command{
    private final Drivetrain m_drivetrain;

    private final DoubleSupplier m_speedSupplier;
    private final DoubleSupplier m_rotationSupplier;

    // Constructor for the command
    public ArcadeDrive(Drivetrain drivetrain, DoubleSupplier speedSupplier, DoubleSupplier rotationSupplier) {
        m_drivetrain = drivetrain;

        m_speedSupplier = speedSupplier;
        m_rotationSupplier = rotationSupplier;
    }

    // Called when the command is initially scheduled.
    @Override
    public void initialize() {}

    // Called every time the scheduler runs while the command is scheduled.
    @Override
    public void execute() {

        // call the arcadeDrive() function in the drivetrain subsystem
        m_drivetrain.arcadeDrive(m_speedSupplier.getAsDouble(), m_rotationSupplier.getAsDouble());
    }
}
