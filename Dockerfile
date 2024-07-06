FROM python:3.9-slim

# Install MkDocs and the material theme
RUN pip install mkdocs mkdocs-material

# Copy your MkDocs site into the container
COPY . /app
WORKDIR /app


# Expose the MkDocs port
EXPOSE 8000

# Start the MkDocs server
CMD ["mkdocs", "serve", "--dev-addr=0.0.0.0:80"]
