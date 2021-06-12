// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.SlewRateLimiter;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
// import frc.robot.commands.ArcadeDrive;
import frc.robot.commands.AutoSequences.*;
import frc.robot.commands.ArcadeDrive;
// import frc.robot.commands.DriveStraight;
import frc.robot.commands.IndexCommand;
import frc.robot.commands.IntakeCommand;
import frc.robot.commands.IntakePistons;
import frc.robot.commands.ShooterCommand;
import frc.robot.commands.ShooterPistons;
import frc.robot.commands.TankDrive;
import frc.robot.commands.VisionDrive;
import frc.robot.commands.ShootSpeeds.BlueShoot;
import frc.robot.commands.ShootSpeeds.GreenShoot;
import frc.robot.commands.ShootSpeeds.RedShoot;
import frc.robot.commands.ShootSpeeds.ShootHigh;
import frc.robot.commands.ShootSpeeds.ShootLow;
import frc.robot.commands.ShootSpeeds.YellowShoot;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.IndexSubsystem;
import frc.robot.subsystems.IntakeSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private final DriveTrain m_driveTrain = new DriveTrain();
  private final IndexSubsystem m_indexer = new IndexSubsystem();
  private final IntakeSubsystem m_intake = new IntakeSubsystem();
  private final ShooterSubsystem m_shooter = new ShooterSubsystem();

  private final CrusaderController m_controller0 = new CrusaderController(Constants.kController0);
  private final CrusaderController m_controller1 = new CrusaderController(Constants.kController1);

  private final SlewRateLimiter m_speedLimiter = new SlewRateLimiter(3); // Limits joystick acceleration

  // private final AutoDrive m_autoDrive = new AutoDrive(m_driveTrain);
  // private final RotateDrive m_rotateDrive = new RotateDrive(m_driveTrain);
  private final AutoSlalom m_autoSlalom = new AutoSlalom(m_driveTrain);
  private final AutoBounce m_autoBounce = new AutoBounce(m_driveTrain);
  private final GalacticPath1 m_galacticpath1 = new GalacticPath1(m_driveTrain, m_intake, m_indexer);

  private final SendableChooser<Object> m_chooser = new SendableChooser<Object>();

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();

    // Set default commands
    setDefaultCommands();

    // Show what command each subsystem is running on the SmartDashboard
    SmartDashboard.putData(m_driveTrain);
    SmartDashboard.putData(m_indexer);
    SmartDashboard.putData(m_intake);
    SmartDashboard.putData(m_shooter);

    // Configure auto chooser
    m_chooser.setDefaultOption("autoSlalom", m_autoSlalom);
    m_chooser.addOption("autoBounce", m_autoBounce);
    SmartDashboard.putData("Autonomous", m_chooser);
  }

  /** Set default commands for subsystems based on controller input*/
  private void setDefaultCommands() {

    m_driveTrain.setDefaultCommand(
      new TankDrive(
        () -> -m_controller0.getLeftStickY(), 
        () -> -m_controller0.getRightStickY(), m_driveTrain) // Inverted because XboxController reads upward joystick as negative
    );

    // m_driveTrain.setDefaultCommand(
    //   new ArcadeDrive(
    //     () -> m_controller0.getLeftTrigger(), rotate, driveTrain)
    // );
    
    m_intake.setDefaultCommand(
      new IntakeCommand(
        () -> m_controller1.getRightStickY(), m_intake)
    );

    m_indexer.setDefaultCommand(
      new IndexCommand(
        () -> m_controller1.getLeftStickY(), m_indexer)
    );

    m_shooter.setDefaultCommand(
      new ShooterCommand(
        () -> m_controller1.getRightTrigger(), m_shooter)
    );

  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // m_controller0.aButton.whenHeld(new VisionDrive(m_driveTrain, 3)); // Green zone
    // m_controller0.bButton.whenHeld(new VisionDrive(m_driveTrain, 0)); // Red zone
    // m_controller0.xButton.whenHeld(new VisionDrive(m_driveTrain, 1)); // Blue zone
    // m_controller0.yButton.whenHeld(new VisionDrive(m_driveTrain, 2)); // Yellow zone

    m_controller0.leftBumper.whenHeld(new ArcadeDrive(() -> -0.85, () -> 0, m_driveTrain));
    m_controller0.rightBumper.whenHeld(new ArcadeDrive(() -> 0.85, () -> 0, m_driveTrain));

    m_controller0.xButton.whenHeld(new IntakePistons(m_intake));
    m_controller0.yButton.whenHeld(new ShooterPistons(m_shooter));
 
    m_controller1.aButton.whileHeld(new GreenShoot(m_shooter));
    m_controller1.bButton.whileHeld(new RedShoot(m_shooter));
    m_controller1.xButton.whileHeld(new BlueShoot(m_shooter));
    m_controller1.yButton.whileHeld(new YellowShoot(m_shooter));

  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_galacticpath1;
  }
}
