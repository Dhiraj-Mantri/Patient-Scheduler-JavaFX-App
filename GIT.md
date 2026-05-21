# GIT Version Control System

Quote from the website:

> Git is a free and open source distributed version control system designed to handle everything from small to very large projects with speed and efficiency.  
> Git is easy to learn and has a tiny footprint with lightning-fast performance. It outclasses SCM tools like Subversion, CVS, Perforce, and ClearCase with features like cheap local branching, convenient staging areas, and multiple workflows.

For a deeper look and good documentation, visit the project's website at [git-scm.com](https://git-scm.com/). They also offer the book *Pro Git* free of charge.

## Quickstart: Getting Up and Running

1. Install Git on your machine if you haven't already.  

   For Windows, [download the 64-bit installer](https://github.com/git-for-windows/git/releases/download/v2.49.0.windows.1/Git-2.49.0-64-bit.exe) from the official website and follow the installer's instructions. Make sure the installer adds Git to the Windows PATH variable.

   Alternatively, you can install it on Windows 10 and above with Winget. Open your terminal and run:  
   `winget install Git.Git`

2. To check if the installation was successful, open a new terminal and run:  
   `git --version`  
   This should output a line like:  
   `git version 2.38.1.windows.1`

3. Set your identity:  
   Run:  
   `git config --global user.name "<your full name>"`  
   to set your name.  
   Run:  
   `git config --global user.email "<you>@uni-weimar.de"`  
   to set your email address.

4. In the open terminal, navigate to the location where you want this project to be stored on your machine. The project will be placed in a separate folder.  

   Alternatively, you can navigate to the right spot in Windows Explorer and then right-click > **Open in Terminal**.

   Then run:  
   `git clone git@https://gitlab.uni-weimar.de/software-engineering/se-lectures/se-sose-2026/patient-scheduler-system.git`

## Next Steps

Familiarize yourself with the basic Git commands:

- `git status`
- `git pull`
- `git add`
- `git commit`
- `git log`
- `git push`

Once you understand these commands, you can explore the Git integration in your editor. If you don't like your IDE’s built-in Git features, there are also third-party tools like **Git Tower**, **TortoiseGit**, **Sourcetree**, etc., that offer a (more) mouse-driven workflow compared to the traditional, purely text-based CLI approach.
