package com.criterya.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.lang.reflect.Field;

import javax.swing.Box;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;
import org.springframework.stereotype.Component;

@Component
public class VideoPanel extends JPanel {
	private static final long serialVersionUID = 6984235223569864264L;
	private String videoFile;
	private double frameNum = 0;
	private JPanel videoPanel;
	private VideoCapture capture;
	private static Mat image;
	
	static{
		try {

			System.setProperty("java.library.path", "lib/opencv/x64");

			Field fieldSysPath = ClassLoader.class.getDeclaredField("sys_paths");
			fieldSysPath.setAccessible(true);
			fieldSysPath.set(null, null);

		} catch (Exception ex) {
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}

		// load the required libraries
		System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
		System.loadLibrary("opencv_ffmpeg2410_64");

		image = Mat.eye( 3, 3, CvType.CV_8UC1 );
	}

	/**
	 * Create the panel.
	 */
	public VideoPanel() {
		setLayout(new BorderLayout(0, 0));
		
		JPanel comandosPanel = new JPanel();
		add(comandosPanel, BorderLayout.SOUTH);
		
		JButton prevButton = new JButton("<<");
		prevButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		JButton nextButton = new JButton(">>");
		GroupLayout gl_comandosPanel = new GroupLayout(comandosPanel);
		gl_comandosPanel.setHorizontalGroup(
			gl_comandosPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_comandosPanel.createSequentialGroup()
					.addGap(101)
					.addComponent(prevButton)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(nextButton)
					.addContainerGap(205, Short.MAX_VALUE))
		);
		gl_comandosPanel.setVerticalGroup(
			gl_comandosPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_comandosPanel.createSequentialGroup()
					.addGroup(gl_comandosPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(prevButton)
						.addComponent(nextButton))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		comandosPanel.setLayout(gl_comandosPanel);
		
		videoPanel = new JPanel();
		videoPanel.setBackground(Color.BLACK);
		add(videoPanel, BorderLayout.CENTER);
		videoPanel.setLayout(new BorderLayout(0, 0));
		
		java.awt.Component verticalStrut = Box.createVerticalStrut(20);
		videoPanel.add(verticalStrut, BorderLayout.CENTER);
	}

	public String getVideoFile() {
		return videoFile;
	}

	public void setVideoFile(String videoFile) {
		this.videoFile = videoFile;
		capture = new VideoCapture();
		image = new Mat();
		System.out.println("Abriendo video :"+capture.open(this.videoFile));
	}
	
	private void showFrame(){
		boolean result = capture.read(image);
		System.out.println("Leyendo imagen: "+result);
		if (result) {
            ImageIcon img = new ImageIcon(Mat2BufferedImage(image));
            videoPanel.removeAll();
            videoPanel.add(new JLabel(img));
            videoPanel.revalidate();
            videoPanel.repaint();
        }else
        	System.err.println("No quiere");
	}
	
	public static BufferedImage Mat2BufferedImage(Mat m){
	    //source: http://answers.opencv.org/question/10344/opencv-java-load-image-to-gui/
	    //Fastest code
	    //The output can be assigned either to a BufferedImage or to an Image

	     int type = BufferedImage.TYPE_BYTE_GRAY;
	     if ( m.channels() > 1 ) {
	         type = BufferedImage.TYPE_3BYTE_BGR;
	     }
	     int bufferSize = m.channels()*m.cols()*m.rows();
	     byte [] b = new byte[bufferSize];
	     m.get(0,0,b); // get all the pixels
	     BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
	     final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
	     System.arraycopy(b, 0, targetPixels, 0, b.length);  
	     return image;
	    }

	public double getFrameNum() {
		return frameNum;
	}

	public void setFrameNum(double frameNum) {
		this.frameNum = frameNum;
		System.out.println("Seteando frameNum: "+capture.set(1, frameNum));
		showFrame();
	}
}
