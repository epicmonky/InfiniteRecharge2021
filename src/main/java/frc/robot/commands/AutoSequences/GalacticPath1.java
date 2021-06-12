// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AutoSequences;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.AutoIntake;
import frc.robot.commands.BangDrive;
import frc.robot.commands.RotateDrive;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class GalacticPath1 extends SequentialCommandGroup {

  private final DriveTrain m_driveTrain;
  private final IntakeSubsystem m_intake;
  private final IndexSubsystem m_indexer;

  /** Creates a new GalacticPath1. */
  public GalacticPath1(DriveTrain driveTrain, IntakeSubsystem intake, IndexSubsystem indexer) {
    m_driveTrain = driveTrain;
    m_intake = intake;
    m_indexer = indexer;

    addCommands(
      // Drive to C3
      new ParallelCommandGroup(
        new BangDrive(m_driveTrain, -1.2, -0.6),
        new AutoIntake(m_intake, m_indexer, 3)
      ),
      // new BangDrive(m_driveTrain, -1.1, -0.6),

      // new AutoIntake(intake, indexer, 2),

      // Rotate to D5
      new RotateDrive(m_driveTrain, 25),

      // Drive to D5
      new BangDrive(m_driveTrain, -1.7, -0.7),

      new AutoIntake(intake, indexer, 1),

      // Rotate to A6
      new RotateDrive(m_driveTrain, -71.56),

      // Drive to A6
      new BangDrive(m_driveTrain, 2.39, 0.7),

      new AutoIntake(m_intake, m_indexer, 1),

      new RotateDrive(m_driveTrain, -90),

      new BangDrive(m_driveTrain, 2, 0.7)
    );
  }
}
