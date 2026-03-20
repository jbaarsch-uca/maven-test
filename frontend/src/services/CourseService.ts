import { CourseResponse, CourseRequest } from '../types/student-reg-types';

const BASE_URL = import.meta.env.VITE_API_BASE_URL + "/courses" || 'http://localhost:8080/api';

export const CourseService = {
    // Matches @GetMapping in CourseController
    async getAllCourses(): Promise<CourseResponse[]> {
        const response = await fetch(BASE_URL);
        if (!response.ok) throw new Error('Failed to load courses');
        return response.json();
    },

    // Matches @PutMapping("/addStudent/{courseId}")
    async addStudentToCourse(courseId: number, studentId: number): Promise<string> {
        const response = await fetch(`${BASE_URL}/addStudent/${courseId}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            // Matches @RequestBody long studentId
            body: JSON.stringify(studentId)
        });

        const message = await response.text();
        if (!response.ok) throw new Error(message); // Catches NullCourseException or "Course is full"
        return message;
    },

    // GET /api/courses/getEnrollment/{courseId}
    async getEnrollmentCount(courseId: number): Promise<number> {
        const response = await fetch(`${BASE_URL}/courses/getEnrollment/${courseId}`);
        if (!response.ok) return -1;
        return response.json();
    }
};