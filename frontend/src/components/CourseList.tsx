import React, { useEffect, useState } from 'react';
import { CourseResponse } from '../types/student-reg-types';
import { CourseService } from '../services/CourseService';

const CourseList: React.FC = () => {
    // State to store our courses
    const [courses, setCourses] = useState<CourseResponse[]>([]);
    const [loading, setLoading] = useState<boolean>(true);
    const [error, setError] = useState<string | null>(null);

    // Fetch data on component load
    useEffect(() => {
        const loadCourses = async () => {
            try {
                const data = await CourseService.getAllCourses();
                setCourses(data);
            } catch (err) {
                setError(err instanceof Error ? err.message : 'An error occurred');
            } finally {
                setLoading(false);
            }
        };

        loadCourses();
    }, []);

    if (loading) return <div>Loading courses from Spring Boot...</div>;
    if (error) return <div style={{ color: 'red' }}>Error: {error}</div>;

    return (
        <div className="course-container">
            <h1>Available Courses</h1>
            <table>
                <thead>
                <tr>
                    <th>Course Name</th>
                    <th>Instructor</th>
                    <th>Room</th>
                    <th>Enrolled Count</th>
                    <th>Roster</th>
                </tr>
                </thead>
                <tbody>
                {courses.map(course => (
                    <tr key={course.id}>
                        <td>{course.name}</td>
                        <td>{course.instructor}</td>
                        <td>{course.room}</td>
                        <td>{course.roster.length}</td>
                        <td>{course.roster.join(', ') || 'Empty'}</td>
                    </tr>
                ))}
                </tbody>
            </table>
        </div>
    );
};

export default CourseList;