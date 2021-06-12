// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class DriveBackStraight extends CommandBase {

  private final DriveTrain m_driveTrain;
  private double distanceError;
  private double kP_distance = 0.4;
  private double kSpeed;

  private double angleError;
  private double kP_angle = 0.01;
  private double kBalance;

  private double distance; // meters
  private double MAX_SPEED = 1;

  /** Creates a new DriveBackStraight. */
  public DriveBackStraight(DriveTrain driveTrain, double inDistance) {
    m_driveTrain = driveTrain;
    distance = inDistance;

    addRequirements(m_driveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_driveTrain.resetGyro();
    m_driveTrain.resetEncoders();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if(!inRange()) {
      distanceError = distance - m_driveTrain.getAverageEncoderDistance();
      kSpeed = kP_distance * distanceError;
      if (Math.abs(kSpeed) > MAX_SPEED) {
        kSpeed = Math.copySign(MAX_SPEED, kSpeed);
      }

      angleError = m_driveTrain.getHeading();
      kBalance = 1 + kP_angle * angleError * Math.signum(kSpeed);
      m_driveTrain.tankDrive(kSpeed, kSpeed * kBalance);
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
    if (inRange()) {
      return true;
    }
    else {
      return false;
    }
  }

  public boolean inRange() {
    if(Math.abs(m_driveTrain.getAverageEncoderDistance() - distance) < 0.15) {
      return true;
    }
    else {
      return false;
    }
  }
}
