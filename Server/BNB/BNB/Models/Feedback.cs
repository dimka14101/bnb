namespace BNB
{
    using System;
    using System.Collections.Generic;
    using System.ComponentModel.DataAnnotations;
    using System.ComponentModel.DataAnnotations.Schema;
    using System.Data.Entity.Spatial;

    [Table("Feedback")]
    public partial class Feedback
    {
        [Key]
        [Column(Order = 0)]
        [StringLength(50)]
        public string FUName { get; set; }

        [StringLength(50)]
        public string FUPhone { get; set; }

        [Key]
        [Column(Order = 1)]
        [StringLength(50)]
        public string FUEmail { get; set; }

        [Key]
        [Column(Order = 2)]
        public string FUFeedbackDetail { get; set; }

        [Key]
        [Column(Order = 3)]
        public bool FUNeedResponce { get; set; }

        [Key]
        [Column(Order = 4)]
        public DateTime? FCreateDate { get; set; }
    }
}
