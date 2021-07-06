// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;


public class Pulley extends SubsystemBase {
  private final DoubleSolenoid dsolenoid;
  private boolean isIn;  
  private final VictorSP pulleymotor;
  private final double shooter_threshold = 0.2;
  private final double limit = 0.75;
 
  /** Creates a new Pulley. */
  public Pulley() {
      dsolenoid = new DoubleSolenoid(
        Constants.PULLEY_SOLENOID_DEPLOY,
        Constants.PULLEY_SOLENOID_RETRACT);
      isIn = false;

      pulleymotor = new VictorSP(Constants.PULLEY_MOTOR_VICTORSPX0);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void push() {
    if (!isIn) {
      dsolenoid.set(Value.kForward);
      isIn = !isIn;
    } else {
      dsolenoid.set(Value.kReverse);
      isIn = !isIn;
    }
  }

  public void pull(double input) {
    if (input > shooter_threshold) {
        pulleymotor.set(input * limit);
    } else if (input < -shooter_threshold) {
        pulleymotor.set(input * limit);
    } else {
        stop();
      }
  }

  public void stop() {
      pulleymotor.set(0);
  }
}
