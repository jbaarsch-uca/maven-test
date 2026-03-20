import {Student} from "../types/student-reg-types";

const BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api';

export const StudentService = {


    // GET /api/students
    async getAllStudents(): Promise<Student[]> {
        const response = await fetch(`${BASE_URL}/students`);
        return response.json();
    },

    // POST /api/students
    async createStudent(student: Student): Promise<{message: string}> {
        const response = await fetch(`${BASE_URL}/students`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(student)
        });
        return response.json();
    }
};