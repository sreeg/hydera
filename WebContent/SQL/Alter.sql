ALTER TABLE `school`.`attendance` 
CHANGE COLUMN `dayspresent` `dayspresent` DOUBLE NULL DEFAULT NULL COMMENT '' ;


ALTER TABLE `school`.`student` 
ADD COLUMN `admissionid` VARCHAR(200) NULL COMMENT '' AFTER `mothermobile`;


ALTER TABLE `school`.`student` 
ADD COLUMN `mothermobile` VARCHAR(45) NULL COMMENT '' AFTER `motherdetails`;


Update student set class='X' where class='Class X';
Update student set class='IX' where class='Class IX';
Update student set class='VIII' where class='Class VIII';
Update student set class='VII' where class='Class VII';
Update student set class='VI' where class='Class VI';
Update student set class='V' where class='Class V';
Update student set class='IV' where class='Class IV';
Update student set class='III' where class='Class III';
Update student set class='II' where class='Class II';
Update student set class='I' where class='Class I';
Update student set class='PP2' where class='UKG';
Update student set class='PP1' where class='LKG';

Update feedetails set classname='X' where classname='Class X';
Update feedetails set classname='IX' where classname='Class IX';
Update feedetails set classname='VIII' where classname='Class VIII';
Update feedetails set classname='VII' where classname='Class VII';
Update feedetails set classname='VI' where classname='Class VI';
Update feedetails set classname='V' where classname='Class V';
Update feedetails set classname='IV' where classname='Class IV';
Update feedetails set classname='III' where classname='Class III';
Update feedetails set classname='II' where classname='Class II';
Update feedetails set classname='I' where classname='Class I';
Update feedetails set classname='PP2' where classname='UKG';
Update feedetails set classname='PP1' where classname='LKG';