// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AutoSequences;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.IndexCommand;
import frc.robot.commands.VisionDrive;
import frc.robot.commands.ShootSpeeds.ShootHigh;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.ShooterSubsystem;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class GreenZoneShoot extends SequentialCommandGroup {

  private final DriveTrain m_driveTrain;
  private final IndexSubsystem m_indexer;
  private final ShooterSubsystem m_shooter;

  /** Creates a new GreenZoneShoot. */
  public GreenZoneShoot(DriveTrain drivetrain, IndexSubsystem indexer, ShooterSubsystem shooter) {
    m_driveTrain = drivetrain;
    m_indexer = indexer;
    m_shooter = shooter;

    addCommands(
      // Lines up robot with port
      new VisionDrive(m_driveTrain, 0),

      new ParallelCommandGroup(
        new IndexCommand(() -> 0.8, m_indexer),
        
        new ShootHigh(m_shooter)
      )
    );
  }
}
