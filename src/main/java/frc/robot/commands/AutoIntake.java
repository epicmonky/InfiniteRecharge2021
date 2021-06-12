// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

public class AutoIntake extends CommandBase {

  private final IntakeSubsystem m_intake;
  private final IndexSubsystem m_indexer;
  private double final_time;
  private Timer timer;

  /** Creates a new AutoIntake. */
  public AutoIntake(IntakeSubsystem intake, IndexSubsystem indexer, double time) {
    m_intake = intake;
    m_indexer = indexer;
    final_time = time;
    timer = new Timer();

    addRequirements(m_intake, m_indexer);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    timer.start();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_intake.intake(0.8);
    m_indexer.set(0.8);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_intake.intake(0);
    m_indexer.set(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (timer.get() > final_time) {
      return true;
    }
    else {
      return false;
    }
  }
}
