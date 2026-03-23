import React, {useState} from 'react';
import { StudentService } from '../services/StudentService';
import {CourseResponse, Student} from '../types/student-reg-types';

const StudentList = () => {
    // State to store our courses
    const [students, setStudents] = useState<Student[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);
    const [newStudentName, setNewStudentName] = useState("");
    const [newStudentGPA, setNewStudentGPA] = useState("");
    const [editingStudentId, setEditingStudentId] = useState<number | null>(null);
    const [editForm, setEditorForm] = useState({
        name: "",
        gpa: ""
    });

};

export default  StudentList;
