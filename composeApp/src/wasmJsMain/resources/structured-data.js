const structuredData = {
  "@context": "https://schema.org",
  "@type": "Person",
  "name": "Yashvant Yadav",
  "jobTitle": "Android Developer",
  "description": "Android Developer from Sikrara, Jaunpur, India. Computer Science student at KIET specializing in Kotlin and mobile app development.",
  "address": {
    "@type": "PostalAddress",
    "addressLocality": "Sikrara",
    "addressRegion": "Jaunpur",
    "addressCountry": "India"
  },
  "alumniOf": {
    "@type": "Organization",
    "name": "KIET Group of Institutions"
  },
  "knowsAbout": [
    "Android Development",
    "Kotlin Programming",
    "Jetpack Compose",
    "Mobile App Development"
  ],
  "url": "https://yourdomain.com",
  "sameAs": [
    "https://linkedin.com/in/yashvant-yadav",
    "https://github.com/yashvant-yadav"
  ]
};

// Inject structured data into page
const script = document.createElement('script');
script.type = 'application/ld+json';
script.textContent = JSON.stringify(structuredData);
document.head.appendChild(script);