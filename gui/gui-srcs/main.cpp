#include "mainwindow.h"

QLineEdit *text1;
QLineEdit *text2;
QPushButton *button1;
QPushButton *button2;

void download(void)
{
    std::string str;
    QString s = text2->text();
    system(("ipfs get " + s.toStdString()).c_str());
	text2->setText("");
}

void upload(void)
{
    if (button2->text() == "Uploaded!")
        return ;
    QString s = text1->text();
    std::string str = s.toStdString();
    system(("ipfs add -r " + str + " >> uploads.log").c_str());
    text1->setText("");
}

void    close_daemon(void)
{
    exit(0);
}


void launch_daemon(void)
{
    if ((button1->text().toStdString()) == "Launch Daemon")
    {
        system("ipfs daemon &");
        button1->setText("-Daemon Running-");
    }
}

int main(int argc, char *argv[])
{
    QApplication app(argc, argv);

    QWidget *window = new QWidget;
    button1 = new QPushButton("Launch Daemon");
    QObject::connect(button1,&QPushButton::clicked,launch_daemon);
    button2 = new QPushButton("Upload");
    QObject::connect(button2,&QPushButton::clicked,upload);
    QPushButton *button3 = new QPushButton("Download");
    QObject::connect(button3,&QPushButton::clicked,download);
    QPushButton *button4 = new QPushButton("Exit");
    QObject::connect(button4,&QPushButton::clicked,close_daemon);

    text1 = new QLineEdit();
    text1->setPlaceholderText("Upload (/usr/directory/...)");
    text2 = new QLineEdit();
    text2->setFixedWidth(400);
    text2->setPlaceholderText("Download (Hash)");


    QWidget *widget = new QWidget();
    QGridLayout *layout = new QGridLayout(widget);
    widget->setMaximumWidth(80);
    widget->setMinimumWidth(80);
    layout->addWidget(button1);
    layout->addWidget(text1);
    layout->addWidget(button2);
    layout->addWidget(text2);
    layout->addWidget(button3);
    layout->addWidget(button4);

    window->setLayout(layout);
    window->show();
    return app.exec();
}

