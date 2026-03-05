# React Forms & Validation - Interview Questions

## Q1: How do you handle form submission in React?
**Answer:**
```jsx
function Form() {
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    message: ''
  });

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({
      ...prev,
      [name]: value
    }));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('Submitted:', formData);
    // Send to server
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        name="name"
        value={formData.name}
        onChange={handleChange}
      />
      <button type="submit">Submit</button>
    </form>
  );
}
```

## Q2: How do you validate forms in React?
**Answer:**
```jsx
function FormWithValidation() {
  const [formData, setFormData] = useState({ email: '', password: '' });
  const [errors, setErrors] = useState({});

  const validate = () => {
    const newErrors = {};
    
    if (!formData.email) {
      newErrors.email = 'Email is required';
    } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
      newErrors.email = 'Invalid email format';
    }

    if (!formData.password) {
      newErrors.password = 'Password is required';
    } else if (formData.password.length < 8) {
      newErrors.password = 'Password must be at least 8 characters';
    }

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (validate()) {
      console.log('Form is valid');
      // Submit form
    }
  };

  return (
    <form onSubmit={handleSubmit}>
      <input
        name="email"
        value={formData.email}
        onChange={(e) => setFormData({...formData, email: e.target.value})}
      />
      {errors.email && <span>{errors.email}</span>}

      <input
        name="password"
        type="password"
        value={formData.password}
        onChange={(e) => setFormData({...formData, password: e.target.value})}
      />
      {errors.password && <span>{errors.password}</span>}

      <button type="submit">Submit</button>
    </form>
  );
}
```

## Q3: What are popular form libraries?
**Answer:**

**React Hook Form:**
```jsx
import { useForm } from 'react-hook-form';

function App() {
  const { register, handleSubmit, formState: { errors } } = useForm();

  const onSubmit = (data) => console.log(data);

  return (
    <form onSubmit={handleSubmit(onSubmit)}>
      <input {...register('email', { required: 'Email is required' })} />
      {errors.email && <span>{errors.email.message}</span>}
      <button type="submit">Submit</button>
    </form>
  );
}
```

**Formik:**
```jsx
import { Formik, Form, Field, ErrorMessage } from 'formik';
import * as Yup from 'yup';

const validationSchema = Yup.object({
  email: Yup.string().email('Invalid email').required('Required')
});

function App() {
  return (
    <Formik
      initialValues={{ email: '' }}
      validationSchema={validationSchema}
      onSubmit={(values) => console.log(values)}
    >
      {() => (
        <Form>
          <Field name="email" />
          <ErrorMessage name="email" component="div" />
          <button type="submit">Submit</button>
        </Form>
      )}
    </Formik>
  );
}
```

## Q4: How do you handle file uploads?
**Answer:**
```jsx
function FileUpload() {
  const [file, setFile] = useState(null);
  const [preview, setPreview] = useState(null);

  const handleFileChange = (e) => {
    const selectedFile = e.target.files[0];
    if (selectedFile) {
      setFile(selectedFile);
      
      // Create preview for images
      if (selectedFile.type.startsWith('image/')) {
        const reader = new FileReader();
        reader.onload = (e) => setPreview(e.target.result);
        reader.readAsDataURL(selectedFile);
      }
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    
    const formData = new FormData();
    formData.append('file', file);

    const response = await fetch('/api/upload', {
      method: 'POST',
      body: formData
    });

    const result = await response.json();
    console.log('Upload result:', result);
  };

  return (
    <form onSubmit={handleSubmit}>
      <input type="file" onChange={handleFileChange} />
      {preview && <img src={preview} alt="Preview" />}
      <button type="submit">Upload</button>
    </form>
  );
}
```

## Q5: How do you handle dynamic forms?
**Answer:**
```jsx
function DynamicForm() {
  const [fields, setFields] = useState([
    { id: 1, name: '', email: '' }
  ]);

  const handleAddField = () => {
    setFields([
      ...fields,
      { id: Date.now(), name: '', email: '' }
    ]);
  };

  const handleRemoveField = (id) => {
    setFields(fields.filter(field => field.id !== id));
  };

  const handleFieldChange = (id, fieldName, value) => {
    setFields(fields.map(field =>
      field.id === id
        ? { ...field, [fieldName]: value }
        : field
    ));
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log('Form data:', fields);
  };

  return (
    <form onSubmit={handleSubmit}>
      {fields.map(field => (
        <div key={field.id}>
          <input
            value={field.name}
            onChange={(e) => handleFieldChange(field.id, 'name', e.target.value)}
            placeholder="Name"
          />
          <input
            value={field.email}
            onChange={(e) => handleFieldChange(field.id, 'email', e.target.value)}
            placeholder="Email"
          />
          <button onClick={() => handleRemoveField(field.id)}>Remove</button>
        </div>
      ))}
      <button type="button" onClick={handleAddField}>Add Field</button>
      <button type="submit">Submit</button>
    </form>
  );
}
```

## Q6: How do you handle file input with multiple files?
**Answer:**
```jsx
function MultiFileUpload() {
  const [files, setFiles] = useState([]);

  const handleMultipleFiles = (e) => {
    const selectedFiles = Array.from(e.target.files);
    setFiles(prev => [...prev, ...selectedFiles]);
  };

  const handleRemoveFile = (index) => {
    setFiles(files.filter((_, i) => i !== index));
  };

  const handleUpload = async () => {
    const formData = new FormData();
    files.forEach(file => formData.append('files', file));

    const response = await fetch('/api/upload-multiple', {
      method: 'POST',
      body: formData
    });

    const result = await response.json();
    console.log('Upload result:', result);
  };

  return (
    <div>
      <input 
        type="file" 
        multiple 
        onChange={handleMultipleFiles}
      />
      <ul>
        {files.map((file, index) => (
          <li key={index}>
            {file.name}
            <button onClick={() => handleRemoveFile(index)}>Remove</button>
          </li>
        ))}
      </ul>
      <button onClick={handleUpload}>Upload All</button>
    </div>
  );
}
```

