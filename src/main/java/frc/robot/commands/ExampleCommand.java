/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands;

import frc.robot.subsystems.ExampleSubsystem;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;

// import org.opencv.core.Mat;
// import org.opencv.videoio.VideoCapture;

import edu.wpi.first.wpilibj2.command.CommandBase;
// import java.awt.image.BufferedImage;
// import java.awt.image.DataBufferByte;

// import javax.swing.ImageIcon;
// import javax.swing.JFrame;
// import javax.swing.JLabel;

// import java.awt.FlowLayout;


/**
 * An example command that uses an example subsystem.
 */
public class ExampleCommand extends CommandBase {
  @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField"})
  private final ExampleSubsystem m_subsystem;
  // VideoCapture camera;

  /**
   * Creates a new ExampleCommand.
   *
   * @param subsystem The subsystem used by this command.
   */
  public ExampleCommand(ExampleSubsystem subsystem) {
    m_subsystem = subsystem;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(subsystem);
    // camera = new VideoCapture(0);
    // if(camera.isOpened()){
    //   System.out.println("Camera is ready");
    // }
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    NetworkTableInstance inst = NetworkTableInstance.getDefault();
    NetworkTable table = inst.getTable("myContoursReport");
    System.out.print(table.getKeys());
  
    
    // Mat frame = new Mat();
    // camera.read(frame);

  }

  // public BufferedImage mat2BufferedImage(Mat m){
  //   int bufferSize = m.channels()*m.cols()*m.rows();
  //   byte [] b = new byte[bufferSize];
  //   m.get(0,0,b);
  //   BufferedImage image = new BufferedImage(m.cols(), m.rows(), BufferedImage.TYPE_INT_RGB);
  //   final byte [] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
  //   System.arraycopy(b, 0, targetPixels, 0, b.length);
  //   return image;
  // }

  // public void displayImage(BufferedImage img2){
  //   ImageIcon icon = new ImageIcon(img2);
  //   JFrame frame = new JFrame();
  //   frame.setLayout(new FlowLayout());
  //   frame.setSize(img2.getWidth(null)+50,img2.getHeight(null)+50);
  //   JLabel label = new JLabel();
  //   label.setIcon(icon);
  //   frame.add(label);
  //   frame.setVisible(true);
  //   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  // }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}