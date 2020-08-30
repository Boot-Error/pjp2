# Git Assigment

1. Setup a Local Repository

	git init
	
	echo "a file" > file.txt
	git add file.txt
	git commit -m "Initial Commit"
	
2. Setuo a Remote Repository

	git remote add origin https://github.com/Boot-Error/pjp2.git
	git push -u origin master

	This is a [remote repo](https://github.com/Boot-Error/pjp2)

3. Create Local Branches [Feature, Dev, QA, Prod, Delivery]

	git branch feature
	git branch Dev
	git branch QA
	git branch Delivery

4. Add files, make changes, add folders, remove folders

	touch file2.txt
	mkdir feature1
	echo "another text" > file.txt

5. Check-in, state, commit push files to feature

	git checkout feature
	git status
	git add .
	
	git commit -m "added stuff to feature"
	git push origin feature


