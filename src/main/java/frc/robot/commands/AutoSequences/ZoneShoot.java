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
public class ZoneShoot extends SequentialCommandGroup {

  private final DriveTrain m_driveTrain;
  private final IndexSubsystem m_indexer;
  private final ShooterSubsystem m_shooter;
  private int m_zone;

  private final double[] redZoneOffset = {0.33, -6.58}; // [tx, ty]
  private final double[] blueZoneOffset = {-1.77, -7.22};
  private final double[] yellowZoneOffset = {0.17, 2.73};

  
  /** Creates a new ZoneShoot. */
  public ZoneShoot(DriveTrain driveTrain, IndexSubsystem indexer, ShooterSubsystem shooter, int zone) {
    m_driveTrain = driveTrain;
    m_indexer = indexer;
    m_shooter = shooter;
    m_zone = zone;

    addCommands(
      // Line up robot with port
      new VisionDrive(m_driveTrain, zone),

      // Run indexer and shooter
      new ParallelCommandGroup(
        new IndexCommand(() -> 0.8, m_indexer),
        new ShootHigh(m_shooter)
      )
    );
  }
}
