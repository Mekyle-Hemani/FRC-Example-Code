package frc.robot.subsystems; //This is the package declaration. Packages are used to organize code into namespaces to keep it modular and easier to manage.

import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

public class RobotDriveSystem {
  
  private final DifferentialDrive m_robotDrive;

  private final PWMSparkMax m_leftMotor1;
  private final PWMSparkMax m_leftMotor2;
  private final PWMSparkMax m_rightMotor1;
  private final PWMSparkMax m_rightMotor2;

  public RobotDriveSystem() {
    //Initialize the motor controllers for left and right motors
    m_leftMotor1 = new PWMSparkMax(0);
    m_leftMotor2 = new PWMSparkMax(2);
    m_rightMotor1 = new PWMSparkMax(1);
    m_rightMotor2 = new PWMSparkMax(3);

    //Invert the right motors
    m_rightMotor1.setInverted(true);
    m_rightMotor2.setInverted(true);

    //Initialize DifferentialDrive
    m_robotDrive = new DifferentialDrive(m_leftMotor1::set, m_rightMotor1::set);

    //Register motors to the SendableRegistry
    SendableRegistry.addChild(m_robotDrive, m_leftMotor1);
    SendableRegistry.addChild(m_robotDrive, m_leftMotor2);
    SendableRegistry.addChild(m_robotDrive, m_rightMotor1);
    SendableRegistry.addChild(m_robotDrive, m_rightMotor2);
  }

  public void drive(double leftSpeed, double rightSpeed) {
    //Control the lead motors
    m_robotDrive.tankDrive(leftSpeed, rightSpeed);

    //Control the follower motors manually
    m_leftMotor2.set(leftSpeed);
    m_rightMotor2.set(rightSpeed);
  }
}