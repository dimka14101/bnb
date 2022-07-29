namespace BNB
{
    using System;
    using System.Data.Entity;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Linq;

    public partial class BNBEntityDataModel : DbContext
    {
        public BNBEntityDataModel()
            : base("name=BNBEntityDataModel")
        {
        }

        public virtual DbSet<Author> Author { get; set; }
        public virtual DbSet<Location> Location { get; set; }
        public virtual DbSet<Sensor> Sensor { get; set; }
        public virtual DbSet<Feedback> Feedback { get; set; }

        protected override void OnModelCreating(DbModelBuilder modelBuilder)
        {
            modelBuilder.Entity<Sensor>()
                .HasOptional(e => e.Author)
                .WithRequired(e => e.Sensor);

            modelBuilder.Entity<Sensor>()
                .HasOptional(e => e.Location)
                .WithRequired(e => e.Sensor);
        }
    }
}
