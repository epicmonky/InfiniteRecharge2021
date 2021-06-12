// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class TimedBackwards extends CommandBase {

  DriveTrain m_driveTrain;
  double start = 0;
  double time;
  double left, right;

  /** Creates a new TimedBackwards. */
  public TimedBackwards(DriveTrain driveTrain, double inTime, double leftSpeed, double rightSpeed) {
    m_driveTrain = driveTrain;
    time = inTime;
    left = leftSpeed;
    right = rightSpeed;

    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(m_driveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    start = Timer.getFPGATimestamp();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_driveTrain.tankDrive(left, right);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (Timer.getFPGATimestamp() - start > time) {
      return true;
    }
    else {
      return false;
    }
  }
}
