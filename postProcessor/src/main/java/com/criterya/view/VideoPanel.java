package com.criterya.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.lang.reflect.Field;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.highgui.VideoCapture;
import org.springframework.stereotype.Component;

import com.criterya.PostProcessorApplication;

@Component
public class VideoPanel extends JPanel {
	private static final long serialVersionUID = 6984235223569864264L;
	private String videoFile;
	private double frameNum = 0;
	private double maxFrame = 0;
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
		GridBagLayout gbl_comandosPanel = new GridBagLayout();
		gbl_comandosPanel.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_comandosPanel.rowHeights = new int[]{0, 0};
		gbl_comandosPanel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_comandosPanel.rowWeights = new double[]{0.0, Double.MIN_VALUE};
		comandosPanel.setLayout(gbl_comandosPanel);
		
		JButton prevFrameButton = new JButton("<");
		GridBagConstraints gbc_prevFrameButton = new GridBagConstraints();
		gbc_prevFrameButton.insets = new Insets(0, 0, 0, 5);
		gbc_prevFrameButton.gridx = 7;
		gbc_prevFrameButton.gridy = 0;
		comandosPanel.add(prevFrameButton, gbc_prevFrameButton);
		
		JButton playButton = new JButton("play");
		GridBagConstraints gbc_playButton = new GridBagConstraints();
		gbc_playButton.insets = new Insets(0, 0, 0, 5);
		gbc_playButton.gridx = 8;
		gbc_playButton.gridy = 0;
		comandosPanel.add(playButton, gbc_playButton);
		
		JButton nextFrameButton = new JButton(">");
		nextFrameButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				nextFrame();
			}
		});
		GridBagConstraints gbc_nextFrameButton = new GridBagConstraints();
		gbc_nextFrameButton.gridx = 9;
		gbc_nextFrameButton.gridy = 0;
		comandosPanel.add(nextFrameButton, gbc_nextFrameButton);

		videoPanel = new JPanel();
		videoPanel.setBackground(Color.BLACK);
		add(videoPanel, BorderLayout.CENTER);
		videoPanel.setLayout(new BorderLayout(0, 0));
	}

	public String getVideoFile() {
		return videoFile;
	}

	public void setVideoFile(String videoFile) {
		this.videoFile = videoFile;
		capture = new VideoCapture();
		image = new Mat();
		this.frameNum = 0;
		this.maxFrame = 0;
		boolean result = capture.open(this.videoFile);
		PostProcessorApplication.setStatus("Abriendo video "+this.videoFile+": "+result);
		if (result){
			this.maxFrame = capture.get(7);
		}
	}

	private void showFrame(){
		boolean result = capture.set(1, frameNum);
		result = result && capture.read(image);
		PostProcessorApplication.setStatus("Leyendo imagen: "+result);
		if (result) {
			ImageIcon img = new ImageIcon(Mat2BufferedImage(image));
			videoPanel.removeAll();
			videoPanel.add(new JLabel(img));
			videoPanel.revalidate();
			videoPanel.repaint();
		}else
			PostProcessorApplication.setStatus("No se pudo leer imagen.");
	}

	private BufferedImage Mat2BufferedImage(Mat m){
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

	private void nextFrame(){
		if (this.frameNum < this.maxFrame){
			this.frameNum++;
			showFrame();
		}
	}
	
	public void setFrameNum(double frameNum) {
		if (frameNum<this.maxFrame){
			this.frameNum = frameNum;
			showFrame();
		}
	}
}
