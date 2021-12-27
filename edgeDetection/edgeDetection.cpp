#include <iostream>
#include <string>

#include <opencv2/opencv.hpp>
using namespace cv;

void edgeDetect(Mat src, Mat* dest);

int main()
{
	std::string path = "Videos/Disney.mp4";

	Mat srcFrame, dstFrame;
	VideoCapture capture(path);
	capture >> srcFrame;
	int k = 0;

	VideoWriter writer("Videos/Disney2.mp4", VideoWriter::fourcc('M', 'J', 'P', 'G'), 25, srcFrame.size());

	while (k != 27 && !srcFrame.empty())
	{
		//Canny(srcFrame, dstFrame, 50, 100);
		edgeDetect(srcFrame, &dstFrame);
		imshow("Src video", srcFrame);
		imshow("Dst video", dstFrame);
		writer << dstFrame;
		capture >> srcFrame;
		k = waitKey(1);
	}
}

void edgeDetect(Mat src, Mat* dest)
{
	Mat gray;
	cvtColor(src, gray, COLOR_BGR2GRAY);
	cvtColor(src, *dest, COLOR_BGR2GRAY);
	Mat filter1 = (Mat_<double>(3, 3) << -2, 1, -2, 1, 4, 1, -2, 1, -2);
	Mat filter2 = (Mat_<double>(3, 3) << 0, -1, 0, -1, 4, -1, 0, -1, 0);
	Mat filter3 = (Mat_<double>(3, 3) << -1, -1, -1, -1, 8, -1, -1, -1, -1);

	for (int i = 1; i < gray.rows - 1; i++)
	{
		for (int j = 1; j < gray.cols - 1; j++)
		{
			int pixelColor = 0;
			for (int k = 0; k < 3; k++)
			{
				for (int z = 0; z < 3; z++)
				{
					pixelColor += (int)gray.at<uchar>(i - 1 + k, j - 1 + z) * (int)filter1.at<double>(k, z);
				}
			}
			(*dest).at<uchar>(i, j) = pixelColor;
		}
	}
}
