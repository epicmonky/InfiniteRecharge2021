// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class BangDrive extends CommandBase {

  private final DriveTrain m_driveTrain;
  private double distance;
  private double speed;

  /** Creates a new BangDrive. */
  public BangDrive(DriveTrain driveTrain, double inDistance, double inSpeed) {
    m_driveTrain = driveTrain;
    distance = inDistance;
    speed = inSpeed;
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
    m_driveTrain.tankDrive(speed, speed);
    SmartDashboard.putString("Current Command", "BangDrive");
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_driveTrain.stop();
    m_driveTrain.resetEncoders();
    m_driveTrain.resetGyro();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (Math.abs(m_driveTrain.getAverageEncoderDistance()) > Math.abs(distance)) {
      return true;
    }
    else {
      return false;
    }
  }
}
