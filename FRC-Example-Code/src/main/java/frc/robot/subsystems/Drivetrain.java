package frc.robot.subsystems;


import com.ctre.phoenix6.hardware.TalonFX;

import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.Command;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.Follower;
import com.ctre.phoenix6.signals.InvertedValue;
import com.revrobotics.CANSparkLowLevel;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.AnalogEncoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.MotorCANID.DrivetrainID;
import frc.robot.Constants.RobotConstants;

public class Drivetrain extends SubsystemBase{

    // Define all the motor controllers
    private final TalonFX leftLeader = new TalonFX(DrivetrainID.leftLeaderCANID,"rio");
    private final TalonFX rightLeader = new TalonFX(DrivetrainID.rightLeaderCANID,"rio");
    private final TalonFX leftFollower = new TalonFX(DrivetrainID.leftFollowerCANID,"rio");
    private final TalonFX rightFollower = new TalonFX(DrivetrainID.rightFollowerCANID,"rio");

    private final CANSparkMax frontLeftSparkMax = new CANSparkMax(1, CANSparkLowLevel.MotorType.kBrushless);
    private final CANSparkMax frontRightSparkMax = new CANSparkMax(3, CANSparkLowLevel.MotorType.kBrushless);
    private final CANSparkMax backLeftSparkMax = new CANSparkMax(5, CANSparkLowLevel.MotorType.kBrushless);
    private final CANSparkMax backRightSparkMax = new CANSparkMax(7, CANSparkLowLevel.MotorType.kBrushless);

    private final AnalogEncoder frontLeftEncoder = new AnalogEncoder(0);
    private final AnalogEncoder frontRightEncoder = new AnalogEncoder(1);
    private final AnalogEncoder backLeftEncoder = new AnalogEncoder(2);
    private final AnalogEncoder backRightEncoder = new AnalogEncoder(3);

    // Create the differential Drive object
    private final DifferentialDrive m_drive = new DifferentialDrive(
        leftLeader::set, 
        rightLeader::set
    );

    private DoubleSupplier FL_EncoderAngle;
    private DoubleSupplier FR_EncoderAngle;
    private DoubleSupplier BL_EncoderAngle;
    private DoubleSupplier BR_EncoderAngle;

    public Drivetrain(){

        // ----------------------------
        // KRAKEN X60 CONFIGURATION
        // ----------------------------

        var leftConfiguration = new TalonFXConfiguration();
        var rightConfiguration = new TalonFXConfiguration();

        leftConfiguration.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;
        rightConfiguration.MotorOutput.Inverted = InvertedValue.CounterClockwise_Positive;

        leftLeader.getConfigurator().apply(leftConfiguration);
        leftFollower.getConfigurator().apply(leftConfiguration);

        rightLeader.getConfigurator().apply(rightConfiguration);
        rightFollower.getConfigurator().apply(rightConfiguration);

        leftFollower.setControl(new Follower(leftLeader.getDeviceID(), false));
        rightFollower.setControl(new Follower(rightLeader.getDeviceID(), false));

        leftLeader.setSafetyEnabled(true);
        rightLeader.setSafetyEnabled(true);


        // ----------------------------
        // SPARK MAX CONFIGURATION
        // ----------------------------


        // reset factory settings for all motor controllers
        frontLeftSparkMax.restoreFactoryDefaults();
        frontRightSparkMax.restoreFactoryDefaults();
        frontRightSparkMax.restoreFactoryDefaults();
        backRightSparkMax.restoreFactoryDefaults();

        // set current limits for all motor controllers
        frontLeftSparkMax.setSmartCurrentLimit(RobotConstants.driveCurrentLimit);
        frontRightSparkMax.setSmartCurrentLimit(RobotConstants.driveCurrentLimit);
        frontRightSparkMax.setSmartCurrentLimit(RobotConstants.driveCurrentLimit);
        backRightSparkMax.setSmartCurrentLimit(RobotConstants.driveCurrentLimit);



        frontRightSparkMax.setInverted(false);
        frontLeftSparkMax.setInverted(false);
        backLeftSparkMax.setInverted(false);
        backRightSparkMax.setInverted(false);

        // link the motors on the left together and the motors on the right together
        frontRightSparkMax.follow(frontLeftSparkMax);
        backLeftSparkMax.follow(frontLeftSparkMax);
        backRightSparkMax.follow(frontLeftSparkMax);


        // ----------------------------
        // ENCODER CONFIGURATION
        // ----------------------------

        frontLeftEncoder.setPositionOffset(0.122);
        frontRightEncoder.setPositionOffset(0.761);
        backLeftEncoder.setPositionOffset( 0.318);
        backRightEncoder.setPositionOffset( 0.671);
        
        
        


        // ----------------------------
        // SHUFFLEBOARD CONFIGURATION
        // ---------------------------- 
        
        // FL_EncoderAngle = () -> (frontLeftEncoder.getAbsolutePosition() )*360;
        // FR_EncoderAngle = () -> (frontRightEncoder.getAbsolutePosition() )*360;
        // BL_EncoderAngle = () -> (backLeftEncoder.getAbsolutePosition())*360;
        // BR_EncoderAngle = () -> (backRightEncoder.getAbsolutePosition())*360;

        // FL_EncoderAngle = () -> (frontLeftEncoder.getAbsolutePosition()-frontLeftEncoder.getPositionOffset())*360;
        FL_EncoderAngle = () -> Math.abs((frontLeftEncoder.getDistance() % 1)*360);
        FR_EncoderAngle = () -> Math.abs((frontRightEncoder.getDistance() % 1)*360);
        BL_EncoderAngle = () -> Math.abs((backLeftEncoder.getDistance() % 1)*360);
        BR_EncoderAngle = () -> Math.abs((backRightEncoder.getDistance() % 1)*360);


        Shuffleboard.getTab(getName()).addNumber("frontLeft Encoder", FL_EncoderAngle);
        Shuffleboard.getTab(getName()).addNumber("frontRight Encoder", FR_EncoderAngle);
        Shuffleboard.getTab(getName()).addNumber("backLeft Encoder", BL_EncoderAngle);
        Shuffleboard.getTab(getName()).addNumber("backRight Encoder", BR_EncoderAngle);
        
        Shuffleboard.getTab(getName()).add("Reset Encoders", resetEncoders());

    
        
    }

    public void periodic(){
        // FL_EncoderAngle = () -> (frontLeftEncoder.getAbsolutePosition() -0.122) *360;
        // FR_EncoderAngle = () -> (frontRightEncoder.getAbsolutePosition() -0.671) *360;
        // BL_EncoderAngle = () -> (backLeftEncoder.getAbsolutePosition() -0.318) *360;
        // BR_EncoderAngle = () -> (backRightEncoder.getAbsolutePosition() -0.761) *360;
        FL_EncoderAngle = () -> Math.abs((frontLeftEncoder.getDistance() % 1)*360);
        FR_EncoderAngle = () -> Math.abs((frontRightEncoder.getDistance() % 1)*360);
        BL_EncoderAngle = () -> Math.abs((backLeftEncoder.getDistance() % 1)*360);
        BR_EncoderAngle = () -> Math.abs((backRightEncoder.getDistance() % 1)*360);
    }

    public void arcadeDrive(double throttle, double rotation) {
        m_drive.arcadeDrive(throttle, 0);
        frontLeftSparkMax.set(rotation);
    }


    public double encoderoffset(){
        return frontLeftEncoder.getPositionOffset();
    }


    public Command resetEncoders(){
        return run(() -> {
            frontLeftEncoder.reset();
            frontRightEncoder.reset();
            backLeftEncoder.reset();
            backRightEncoder.reset();
        });
    }
    
}
