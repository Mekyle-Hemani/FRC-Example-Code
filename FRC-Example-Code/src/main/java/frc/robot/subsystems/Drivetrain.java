package frc.robot.subsystems;

import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.MotorCANID;
import frc.robot.Constants.RobotConstants;

public class Drivetrain extends SubsystemBase{

    // Define all the motor controllers
    private final CANSparkMax m_frontLeftMotor = new CANSparkMax(MotorCANID.DrivetrainID.frontLeftMotorCANID, CANSparkLowLevel.MotorType.kBrushless);
    private final CANSparkMax m_frontRightMotor = new CANSparkMax(MotorCANID.DrivetrainID.frontRightMotorCANID, CANSparkLowLevel.MotorType.kBrushless);

    private final CANSparkMax m_backLeftMotor = new CANSparkMax(MotorCANID.DrivetrainID.backLeftMotorCANID, CANSparkLowLevel.MotorType.kBrushless);
    private final CANSparkMax m_backRightMotor = new CANSparkMax(MotorCANID.DrivetrainID.backRightMotorCANID, CANSparkLowLevel.MotorType.kBrushless);

    // Create the differential Drive object
    private final DifferentialDrive m_drive = new DifferentialDrive(
        m_frontLeftMotor::set, 
        m_frontRightMotor::set
    );

    public Drivetrain(){

        // reset factory settings for all motor controllers
        m_frontLeftMotor.restoreFactoryDefaults();
        m_backLeftMotor.restoreFactoryDefaults();
        m_frontRightMotor.restoreFactoryDefaults();
        m_backRightMotor.restoreFactoryDefaults();

        // set current limits for all motor controllers
        m_frontLeftMotor.setSmartCurrentLimit(RobotConstants.driveCurrentLimit);
        m_backLeftMotor.setSmartCurrentLimit(RobotConstants.driveCurrentLimit);
        m_frontRightMotor.setSmartCurrentLimit(RobotConstants.driveCurrentLimit);
        m_backRightMotor.setSmartCurrentLimit(RobotConstants.driveCurrentLimit);


        // invert the left motors and leave the right motors not inverted
        m_frontLeftMotor.setInverted(true);
        m_backLeftMotor.setInverted(true);

        m_frontRightMotor.setInverted(false);
        m_backRightMotor.setInverted(false);


        // link the motors on the left together and the motors on the right together
        m_backLeftMotor.follow(m_frontLeftMotor);
        m_backRightMotor.follow(m_frontRightMotor);
    }

    public void arcadeDrive(double throttle, double rotation) {
        m_drive.arcadeDrive(throttle, rotation);
    }
}
