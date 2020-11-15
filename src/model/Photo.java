package model;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.Serializable;

import javafx.scene.image.Image;
import javafx.scene.image.PixelReader;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;

public class Photo implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private transient Image image;
	private String caption;
	private int[][] data;
	private int width;
	private int height;
	private String hashCode;

	public Photo(Image image) {
		try {
			if (image != null)
				this.setImage(image);
			else 
				this.setImage(new Image(new FileInputStream("stock1.jpeg")));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public void setImage(Image image) {
		this.width = (int) image.getWidth();
		this.height = (int) image.getHeight();
		data = new int[this.width][this.height];
		PixelReader pr = image.getPixelReader();
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				data[i][j] = pr.getArgb(i, j);
			}
		}
	}
	
	public void setCaption (String value) {
		this.caption = value;
	}
	
	public String getCaption() {
		return this.caption;
	}
	
	public void setHashCode(String hashCode) {
		this.hashCode = hashCode;
	}
	
	public Image getImage() {
		if (this.image != null) {
			return this.image;
		}
		
	    WritableImage image = new WritableImage(this.width, this.height);
	    PixelWriter pw = image.getPixelWriter();
	    for (int i = 0; i < width; i++) {
	        for (int j = 0; j < height; j++) {
	            pw.setArgb(i, j, data[i][j]);
	        }
	    }
	    return image;
	}
	
	public String toString() {
		return this.hashCode;
	}
	
	public boolean eqauls(Object o) {
		if (o == null || !(o instanceof Photo))
			return false;
		Photo p = (Photo) o;
		return p.toString().equalsIgnoreCase(this.toString());
	}

}
