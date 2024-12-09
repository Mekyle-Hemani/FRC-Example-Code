// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import frc.robot.Constants.OperatorConstants;
import frc.robot.commands.ArcadeDrive;
import frc.robot.subsystems.Drivetrain;
import edu.wpi.first.wpilibj2.command.button.CommandPS5Controller;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and trigger mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  private final Drivetrain drivetrain = new Drivetrain();

  private final CommandPS5Controller driveController = new CommandPS5Controller(OperatorConstants.kDriverControllerPort);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the trigger bindings
    configureBindings();
    
    // Set default commands for subsystems
    setupDefaultCommands();
  }


  // This is used to configure command bindings to buttons, eg. shooting when you pull the trigger on the controller
  private void configureBindings() {
  }


  private void setupDefaultCommands(){
    
    // Sets the drive train to run the ArcadeDrive() command when it doesn't have anything else to run
    drivetrain.setDefaultCommand(
      new ArcadeDrive(
        drivetrain, 
        () -> this.driveController.getLeftY(), 
        () -> this.driveController.getRawAxis(4)
        )
    );
  }
}
