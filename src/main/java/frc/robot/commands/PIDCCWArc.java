// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class PIDCCWArc extends CommandBase {

  private final DriveTrain m_driveTrain;
  private double m_leftVel;
  private double m_rightVel;
  private double m_angle;

  private double angleError;
  private double kP = 0.00005;
  private double kBalance;

  /** Creates a new PIDCCWArc. */
  public PIDCCWArc(DriveTrain driveTrain, double leftVel, double rightVel, double angle) {
    m_driveTrain = driveTrain;
    m_leftVel = leftVel;
    m_rightVel = rightVel;
    m_angle = angle;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_driveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_driveTrain.resetEncoders();
    m_driveTrain.resetGyro();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    angleError = Math.abs(m_driveTrain.getHeading() - m_angle);
    kBalance = angleError * kP;
    if (m_rightVel * kBalance > 1) {
      m_driveTrain.tankDrive(m_leftVel / m_rightVel, 1);
    }
    else {
      m_driveTrain.tankDrive(m_leftVel * kBalance, m_rightVel * kBalance);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_driveTrain.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (m_driveTrain.getHeading() < -m_angle) {
      return true;
    }
    else {
      return false;
    }
  }
}
