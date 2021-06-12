// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands.AutoSequences;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.commands.AutoDrive;
import frc.robot.commands.BangDrive;
import frc.robot.commands.CCWArc;
import frc.robot.commands.RotateDrive;
import frc.robot.subsystems.DriveTrain;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/stable/docs/software/commandbased/convenience-features.html
public class AutoBounce extends SequentialCommandGroup {
  DriveTrain m_driveTrain;

  /** Creates a new AutoBounce. */
  public AutoBounce(DriveTrain driveTrain) {
    m_driveTrain = driveTrain;

    // Add your commands in the addCommands() call, e.g.
    // addCommands(new FooCommand(), new BarCommand());
    addCommands(

      // Drives forward 10 cm
      new BangDrive(m_driveTrain, 0.12, 0.5),

      // Arcs 135 degrees CCW
      new CCWArc(m_driveTrain, 0.486, 0.7, 87), // 85

      // Drive backwards 2 m
      new BangDrive(m_driveTrain, -2.12, -0.7),

      new RotateDrive(m_driveTrain, 50),

      new BangDrive(m_driveTrain, 0.25, 0.5), // 20

      new CCWArc(m_driveTrain, 0.32, 0.77, 47),

      new AutoDrive(m_driveTrain, 2.6),

      new BangDrive(m_driveTrain, -2.1, -0.7),

      new RotateDrive(m_driveTrain, 42),

      new BangDrive(m_driveTrain, 0.9, 0.6),

      new CCWArc(m_driveTrain, 0.35, 0.75, 47),

      new AutoDrive(m_driveTrain, 2.2),

      new CCWArc(m_driveTrain, -0.7, -0.6, 40)
    );
  }
}
