// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveTrain;

public class VisionDrive extends CommandBase {

  DriveTrain m_driveTrain;
  int m_zone;

  NetworkTableEntry tx;
  NetworkTableEntry ty;
  NetworkTableEntry ta;

  double x;
  double y;
  double a;
  private double[] zoneOffset = new double[2];

  double turn = 0;
  double move = 0;

  private final double[] redZoneOffset = {0.33, -6.58}; // [tx, ty]
  private final double[] blueZoneOffset = {-1.77, -7.22};
  private final double[] yellowZoneOffset = {0.17, 2.73};
  private final double[] greenZoneOffset = {-2.06, 18.58};

  /** Creates a new VisionDrive. */
  public VisionDrive(DriveTrain driveTrain, int zone) {
    m_driveTrain = driveTrain;
    m_zone = zone;

    addRequirements(m_driveTrain);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_driveTrain.resetEncoders();
    m_driveTrain.resetGyro();

    m_driveTrain.setTableNumber("camMode", 0);
    m_driveTrain.setTableNumber("ledMode", 3);
    
    switch (m_zone) {
      case 0: 
        m_driveTrain.setTableNumber("pipeline", 0);
        setZoneOffset(redZoneOffset);
        break;
      case 1:
        m_driveTrain.setTableNumber("pipeline", 1);
        setZoneOffset(blueZoneOffset);
        break;
      case 2:
        m_driveTrain.setTableNumber("pipeline", 1); // Same pipeline as above
        setZoneOffset(yellowZoneOffset);
        break;
      case 3:
        m_driveTrain.setTableNumber("pipeline", 2);
        setZoneOffset(greenZoneOffset);
        break;
    }
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    tx = m_driveTrain.getTableEntry("tx");
    ty = m_driveTrain.getTableEntry("ty");
    ta = m_driveTrain.getTableEntry("ta");

    x = tx.getDouble(0.0);
    y = ty.getDouble(0.0);
    a = ta.getDouble(0.0);

    // Post to SmartDashboard
    SmartDashboard.putNumber("LimelightX", x);
    SmartDashboard.putNumber("LimelightY", y);
    SmartDashboard.putNumber("LimelightArea", a);
    SmartDashboard.putNumber("Limelight Pipeline", m_driveTrain.getTableEntry("pipeline").getDouble(9));

    // Move robot based on crosshair position
    if (x > zoneOffset[0] + 2.5) {
      turn = -0.4;
    }
    if (x < zoneOffset[0] - 2.5) {
      turn = 0.4;
    }
    if (y > zoneOffset[1] + 1.5) {
      move = -0.4;
    }
    if (y < zoneOffset[1] - 1.5) {
      move = 0.4;
    }

    m_driveTrain.arcadeDrive(move, turn);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    // m_driveTrain.setTableNumber("ledMode", 1);
    // m_driveTrain.setTableNumber("camMode", 1);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (inXRange(x) && inYRange(y)) {
      return true;
    }
    else {
      return false;
    }
  }

  public boolean inXRange(double x) {
    if (Math.abs(x) < zoneOffset[0] + 2.5) {
      return true;
    }
    else {
      return false;
    }
  }

  public boolean inYRange(double y) {
    if (Math.abs(y) < zoneOffset[1] + 2.5) {
      return true;
    }
    else {
      return false;
    }
  }

  public void setZoneOffset(double[] zoneVals) {
    zoneOffset[0] = zoneVals[0];
    zoneOffset[1] = zoneVals[1];
  }

}
