#!/bin/bash

# branch="refs/heads/org/tango/feature/std"
branch="refs/heads/org/gefeaturetes/feature/std"

prefix=`echo $branch | cut -d'/' -f 3`
orgID=`echo $branch | cut -d'/' -f 4`
workBranch=`echo $branch | cut -d'/' -f 5-`

echo "prefix: $prefix"
echo "orgID: $orgID"
echo "workBranch: $workBranch"

patterns=(".*main.*", ".*feature.*")
for pattern in ${patterns[@]}; do
#   echo "pattern: $pattern"
  if [[ $orgID =~ $pattern ]]; then
    echo "matched: $pattern $orgID"
  fi
done
# pattern=".*feature.*"
# if [[ $orgID =~ $pattern ]]; then
#   echo "matched: $pattern"
# else
#     echo "not matched: $pattern $orgID"
# fi
foo="develop"
pat="(main|develop|feature)"
if [[ $foo =~ $pat ]]; then
  echo "matched: $pat $workBranch"
else
  echo "not matched: $pat $workBranch"
fi